package com.example.job_application_eval.dtos;

import com.example.job_application_eval.entities.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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

    private String jobTitle;

    private ApplicationStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime applicationDate;

}