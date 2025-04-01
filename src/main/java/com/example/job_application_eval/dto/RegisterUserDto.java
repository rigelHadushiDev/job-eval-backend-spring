package com.example.job_application_eval.dto;

import com.example.job_application_eval.entities.Gender;
import com.example.job_application_eval.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private Gender gender;
    private Role role;
}
