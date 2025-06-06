package com.example.job_application_eval.dtos;

import com.example.job_application_eval.entities.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationHighRoleDto {
    private Long jobApplicationId;

    private Long userId;

    private String username;

    private String fullName;

    private String email;

    private Long jobPostingId;

    private String jobTitle;

    private ApplicationStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime applicationDate;

    private Double generalScore;

    private Double educationScore;

    private Double englishScore;

    private Double skillsScore;

    private Double experienceYearsScore;

    private Double experienceSimilarityScore;





}
