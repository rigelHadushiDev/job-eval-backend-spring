package com.example.job_application_eval.responses;


import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInResponse {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private String role;
}
