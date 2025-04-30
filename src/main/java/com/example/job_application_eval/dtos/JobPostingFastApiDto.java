package com.example.job_application_eval.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPostingFastApiDto {

        private String jobPostingId;
        private String jobPostingTitle;
        private String jobPostingDesc;
        private String requiredEducationLevel;
        private String requiredEnglishLevel;
        private Float requiredExperienceYears;
        private String requiredSkills;
}
