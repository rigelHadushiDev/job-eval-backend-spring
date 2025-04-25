package com.example.job_application_eval.dtos;

import com.example.job_application_eval.entities.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationDto {

    private Long jobApplicationId;

    private Long userId;

    private String username;

    private String fullName;

    private Long jobPostingId;

    private ApplicationStatus status;





}