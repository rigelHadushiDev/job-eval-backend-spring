package com.example.job_application_eval.dtos;

import com.example.job_application_eval.entities.enums.EmploymentType;
import com.example.job_application_eval.validation.OnEditSkills;
import com.example.job_application_eval.validation.OnEditWorkExp;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkExperienceDto {

    @NotNull(message = "Work Experience ID is required.", groups = OnEditWorkExp.class)
    private Long workExperienceId;

    @NotBlank(message = "Job Title cannot be empty.", groups = {Default.class, OnEditWorkExp.class})
    private String jobTitle;

    @NotBlank(message = "Company Name cannot be empty.", groups = {Default.class, OnEditWorkExp.class})
    private String companyName;

    @NotNull(message = "Employment Type is required.", groups = {Default.class, OnEditWorkExp.class})
    private EmploymentType employmentType;

    @NotNull(message = "Start Date is required.", groups = {Default.class, OnEditWorkExp.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date endDate;

    @NotBlank(message = "Description is required.", groups = {Default.class, OnEditWorkExp.class})
    private String description;

    @NotNull(message = "Total work experience years is required.", groups = {Default.class, OnEditWorkExp.class})
    private Double totalYears;

    private Long userId;

    private Boolean finished;
}