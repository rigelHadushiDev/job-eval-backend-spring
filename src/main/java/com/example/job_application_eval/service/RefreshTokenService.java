package com.example.job_application_eval.service;

import com.example.job_application_eval.entities.RefreshTokenEntity;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.responses.LogInResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RefreshTokenService {

    LogInResponse refresh(HttpServletRequest request, HttpServletResponse response);

    RefreshTokenEntity issue(UserEntity user,
                             HttpServletRequest request,
                             HttpServletResponse response);

    RefreshTokenEntity rotateFromCookieOrThrow(HttpServletRequest request,
                                         HttpServletResponse response);

    void revokeCookieToken(HttpServletRequest request, HttpServletResponse response);

    void clearCookie(HttpServletResponse response);
}
