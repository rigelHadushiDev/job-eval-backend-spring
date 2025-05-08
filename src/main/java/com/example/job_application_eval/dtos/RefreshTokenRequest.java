package com.example.job_application_eval.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenRequest {

    @NotNull(message = "Refresh token must not be null")
    @NotEmpty(message = "Refresh token must not be empty")
    private String refreshToken;
}