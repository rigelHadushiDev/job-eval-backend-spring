package com.example.job_application_eval.responses;


import com.example.job_application_eval.entities.Role;
import lombok.*;

@Getter
@Setter
public class LogInResponse {

    private String token;

    private long expiresIn;

    private Role role;

    public LogInResponse(String token, long expiresIn, Role role) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.role = role;
    }
}
