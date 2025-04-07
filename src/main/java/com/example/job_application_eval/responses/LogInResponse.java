package com.example.job_application_eval.responses;


import com.example.job_application_eval.entities.Role;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInResponse {
    private String token;
    private long expiresIn;
    private String role;
}
