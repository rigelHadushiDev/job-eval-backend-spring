package com.example.job_application_eval.dtos;

import com.example.job_application_eval.entities.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationHighRoleDto {
    private Long jobApplicationId;

    private Long userId;

    private String username;

    private String fullName;

    private Long jobPostingId;

    private ApplicationStatus status;

    private Double generalScore;

    private Double educationScore;

    private Double englishScore;

    private Double skillsScore;

    private Double experienceYearsScore;

    private Double experienceSimilarityScore;





}
