package com.example.job_application_eval.dtos;

import com.example.job_application_eval.entities.enums.EducationLevel;
import com.example.job_application_eval.entities.enums.EmploymentType;
import com.example.job_application_eval.entities.enums.ProficiencyLevel;
import com.example.job_application_eval.validation.OnJobPostingEdit;
import jakarta.validation.constraints.*;

import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPostingDto {

    @NotNull(groups = OnJobPostingEdit.class, message = "Job Posting ID is required for editing.")
    private Long jobPostingId;

    @NotNull(groups = {OnJobPostingEdit.class, Default.class}, message = "Employment type is required.")
    private EmploymentType employmentType;

    @NotBlank(groups = {OnJobPostingEdit.class, Default.class},message = "Job title is required.")
    private String jobTitle;

    @NotBlank(groups = {OnJobPostingEdit.class, Default.class},message = "Job description is required.")
    private String jobDescription;

    @NotNull(groups = {OnJobPostingEdit.class, Default.class},message = "Required English level is required.")
    private ProficiencyLevel requiredEnglishLevel;

    @NotNull(groups = {OnJobPostingEdit.class, Default.class},message = "Required education level is required.")
    private EducationLevel requiredEducationLevel;

    @NotNull(groups = {OnJobPostingEdit.class, Default.class},message = "Required years of experience is required.")
    @Min(value = 0, message = "Experience years must be zero or positive.")
    private Integer requiredExperienceYears;

    @NotNull(groups = {OnJobPostingEdit.class, Default.class},message = "Required skills count is required.")
    @Min(value = 0, message = "Skills count must be zero or positive.")
    private Integer requiredSkills;

    private Long userId;
}
