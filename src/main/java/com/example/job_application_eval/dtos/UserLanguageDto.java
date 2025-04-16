package com.example.job_application_eval.dtos;

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
public class UserLanguageDto {


    @NotNull(groups = OnEditLanguage.class, message = "User Language Id is required")
    private Long userLanguageId;

    @NotBlank(groups = {OnEditLanguage.class, Default.class}, message = "Language name can not be empty.")
    private String languageName;

    @NotBlank(groups = {OnEditLanguage.class, Default.class}, message = "ProficiencyL level can not be null.")
    private String proficiencyLevel;

    private Long userId;
}
