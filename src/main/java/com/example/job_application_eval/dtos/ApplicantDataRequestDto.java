package com.example.job_application_eval.dtos;
import com.example.job_application_eval.entities.enums.EducationLevel;
import com.example.job_application_eval.entities.enums.ProficiencyLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicantDataRequestDto {

    private Long userId;
    private String username;
    private List<EducationLevelEntry> educationLevel;
    private ProficiencyLevel englishLevel;
    private List<SkillEntry> skills;
    private Long jobPostingId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class EducationLevelEntry {
        private EducationLevel educationLevel;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SkillEntry {
        private String skillName;
        private int skillProficiency;
    }
}
