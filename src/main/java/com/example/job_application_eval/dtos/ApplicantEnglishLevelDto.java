package com.example.job_application_eval.dtos;

import com.example.job_application_eval.entities.enums.ProficiencyLevel;
import com.example.job_application_eval.validation.OnEditLanguage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicantEnglishLevelDto {


    @NotNull(groups = OnEditLanguage.class, message = "Applicant English Level Id is required")
    private Long applicantEnglishLevelId;

    @NotNull(groups = {OnEditLanguage.class, Default.class}, message = "ProficiencyL level can not be null.")
    private ProficiencyLevel proficiencyLevel;

    private Long userId;
}
