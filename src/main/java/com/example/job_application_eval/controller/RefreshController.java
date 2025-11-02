package com.example.job_application_eval.controller;


import com.example.job_application_eval.responses.LogInResponse;

import com.example.job_application_eval.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refresh")
@RequiredArgsConstructor
public class RefreshController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping
    public ResponseEntity<LogInResponse> refresh(HttpServletRequest request, HttpServletResponse response) {
        LogInResponse refreshToken = refreshTokenService.refresh(request, response);
        return ResponseEntity.ok(refreshToken);
    }

    @PostMapping("/revoke")
    public ResponseEntity<Void> revoke(HttpServletRequest request, HttpServletResponse response) {
        refreshTokenService.revokeCookieToken(request, response);
        return ResponseEntity.noContent().build();
    }
}

