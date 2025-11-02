package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.entities.RefreshTokenEntity;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.repository.RefreshTokenRepository;
import com.example.job_application_eval.responses.LogInResponse;
import com.example.job_application_eval.service.JwtService;
import com.example.job_application_eval.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Value("${application.security.refresh.cookie-name}")
    private String refreshCookieName;

    @Value("${application.security.refresh.ttl-seconds}")
    private long refreshTtlSeconds;

    @Value("${application.security.refresh.cookie-domain}")
    private String cookieDomain;

    @Value("${application.security.refresh.cookie-secure}")
    private boolean cookieSecure;

    @Value("${application.security.refresh.cookie-samesite}")
    private String sameSite;

    private static final SecureRandom RNG = new SecureRandom();

    @Override
    public LogInResponse refresh(HttpServletRequest request, HttpServletResponse response) {
        RefreshTokenEntity rotated = rotateFromCookieOrThrow(request, response);

        UserEntity user = rotated.getUser();
        org.springframework.security.core.userdetails.UserDetails ud =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities("ROLE_" + user.getRole().name())
                        .build();

        String newAccess = jwtService.generateToken(ud);
        long expiresIn = jwtService.getExpirationTime();

        return new LogInResponse(
                user.getUserId(),
                newAccess,
                null,
                expiresIn,
                user.getRole().name(),
                user.isPasswordChanged()
        );
    }

    @Override
    public RefreshTokenEntity rotateFromCookieOrThrow(HttpServletRequest request, HttpServletResponse response) {
        String raw = readCookieOrThrow(request);
        String hash = sha256(raw);

        RefreshTokenEntity current = refreshTokenRepository.findByTokenHash(hash)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalidRefresh"));

        if (!current.isActive()) {
            refreshTokenRepository.findByUserAndRevokedAtIsNull(current.getUser())
                    .forEach(rt -> { rt.setRevokedAt(Instant.now()); refreshTokenRepository.save(rt); });
            clearCookie(response);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "reusedOrExpiredRefresh");
        }


        current.setRevokedAt(Instant.now());
        refreshTokenRepository.save(current);

        RefreshTokenEntity next = issue(current.getUser(), request, response);
        current.setReplacedByTokenHash(next.getTokenHash());
        refreshTokenRepository.save(current);

        return next;
    }

    @Override
    public RefreshTokenEntity issue(UserEntity user, HttpServletRequest request, HttpServletResponse response) {
        String raw = generateOpaqueToken();
        String hash = sha256(raw);
        Instant expires = Instant.now().plusSeconds(refreshTtlSeconds);

        RefreshTokenEntity rt = RefreshTokenEntity.builder()
                .user(user)
                .tokenHash(hash)
                .expiresAt(expires)
                .createdByIp(getIp(request))
                .userAgent(request.getHeader("User-Agent"))
                .build();
        refreshTokenRepository.save(rt);

        setCookie(response, raw, (int) refreshTtlSeconds);
        return rt;
    }

    @Override
    public void revokeCookieToken(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> rawOpt = readCookie(request);
        rawOpt.flatMap(raw -> refreshTokenRepository.findByTokenHash(sha256(raw))).ifPresent(rt -> {
            if (rt.getRevokedAt() == null) {
                rt.setRevokedAt(Instant.now());
                refreshTokenRepository.save(rt);
            }
        });
        clearCookie(response);
    }

    @Override
    public void clearCookie(HttpServletResponse response) {
        setCookie(response, "", 0);
    }

    private String generateOpaqueToken() {
        byte[] bytes = new byte[64]; // 512-bit random
        RNG.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String sha256(String raw) {
        try {
            MessageDigest d = MessageDigest.getInstance("SHA-256");
            return Base64.getEncoder().encodeToString(d.digest(raw.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new IllegalStateException("hash error", e);
        }
    }

    private void setCookie(HttpServletResponse response, String value, int maxAgeSeconds) {

        Cookie c = new Cookie(refreshCookieName, value);
        c.setHttpOnly(true);
        c.setSecure(cookieSecure);
        c.setPath("/");
        if (cookieDomain != null && !cookieDomain.trim().isEmpty()) c.setDomain(cookieDomain);
        c.setMaxAge(maxAgeSeconds);
        response.addCookie(c);

        StringBuilder sb = new StringBuilder();
        sb.append(refreshCookieName).append("=").append(value).append("; Path=/; Max-Age=").append(maxAgeSeconds)
                .append("; HttpOnly");
        if (cookieSecure) sb.append("; Secure");
        if (cookieDomain != null && !cookieDomain.trim().isEmpty()) sb.append("; Domain=").append(cookieDomain);
        if (sameSite != null) sb.append("; SameSite=").append(sameSite);
        response.setHeader("Set-Cookie", sb.toString());
    }

    private Optional<String> readCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return Optional.empty();
        for (Cookie c : request.getCookies()) {
            if (refreshCookieName.equals(c.getName())) return Optional.ofNullable(c.getValue());
        }
        return Optional.empty();
    }

    private String readCookieOrThrow(HttpServletRequest request) {
        return readCookie(request)
                .filter(v -> !v.isEmpty())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "missingRefreshCookie"));
    }

    private String getIp(HttpServletRequest request) {
        String h = request.getHeader("X-Forwarded-For");
        if (h != null && !h.isEmpty()) return h.split(",")[0].trim();
        return request.getRemoteAddr();
    }
}
