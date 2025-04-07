package com.example.job_application_eval.dtos;


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
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private Gender gender;
    private Date birthdate;
    private String address;
    private Integer mobileNumber;
    private String fullName;
}

