package com.example.job_application_eval.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkExperienceFastAPIDto {

    private String workExperienceId;
    private Integer userId;
    private String workExperienceTitle;
    private String workExperienceDescription;
    private Double totalYears;
}
