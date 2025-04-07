package com.example.job_application_eval.config.filters;
import com.example.job_application_eval.config.RouteRoleWhitelist;
import com.example.job_application_eval.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class RoleAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        String method = request.getMethod();

        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        token = token.substring(7);
        String username;
        List<String> roles;

        try {
            username = jwtService.extractUsername(token);
            roles = jwtService.extractRoles(token);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        String normalizedPath = path.replaceAll("/\\d+", "/{userId}").replaceAll("/$", "");
        String routeKey = method + ":" + normalizedPath;

        List<String> allowedRoles = RouteRoleWhitelist.WHITELIST.get(routeKey);

        System.out.println("🔐 Incoming request:");
        System.out.println("➡️  Method: " + method);
        System.out.println("➡️  Path: " + path);
        System.out.println("👤 Username: " + username);
        System.out.println("🔑 Roles: " + roles);
        System.out.println("🧭 Route key: " + routeKey);
        System.out.println("✅ Allowed roles: " + allowedRoles);

        if (allowedRoles == null || roles.stream().noneMatch(allowedRoles::contains)) {
            System.out.println("🚫 Access denied. Your roles do not match.");
            throw new AccessDeniedException("Access Denied");
        }

        System.out.println("✅ Access granted.");

        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        System.out.println("✅ Authorization successful. Proceeding with request.\n");

        filterChain.doFilter(request, response);
    }
}
