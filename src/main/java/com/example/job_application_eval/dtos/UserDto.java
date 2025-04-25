package com.example.job_application_eval.dtos;


import com.example.job_application_eval.entities.enums.Gender;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.validation.OnCreateUser;
import com.example.job_application_eval.validation.OnEditUser;
import com.example.job_application_eval.validation.OnSignUpUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(groups = {OnEditUser.class}, message ="First Name is required.")
    private Long userId;

    @NotBlank(groups = {OnSignUpUser.class, OnCreateUser.class}, message ="First Name is required.")
    private String firstname;

    @NotBlank(groups = {OnSignUpUser.class, OnCreateUser.class}, message = "Last Name is required.")
    private String lastname;

    @NotBlank(groups = {OnSignUpUser.class, OnCreateUser.class}, message = "Username is required.")
    private String username;

    @NotBlank(groups = {OnSignUpUser.class, OnCreateUser.class}, message = "Email is required.")
    @Email(groups = {OnSignUpUser.class, OnCreateUser.class}, message = "Please provide a valid email address.")
    private String email;

    @NotNull(groups = OnCreateUser.class, message = " Role is required.")
    private Role role;

    @NotNull(groups = OnSignUpUser.class, message = "Gender is required.")
    private Gender gender;

    @NotNull(groups = {OnSignUpUser.class}, message = "Birth date is required.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthdate;

    private String mobileNumber;
    private String fullName;
}

