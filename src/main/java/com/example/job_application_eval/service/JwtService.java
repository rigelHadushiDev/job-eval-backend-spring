package com.example.job_application_eval.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface JwtService {

    String extractUsername(String token);

    <T> T extractClaim(String token, java.util.function.Function<io.jsonwebtoken.Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    long getExpirationTime();

    List<String> extractRoles(String token);
}
