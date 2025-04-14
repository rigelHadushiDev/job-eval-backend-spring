package com.example.job_application_eval.dtos;


import com.example.job_application_eval.validation.OnEditSkills;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillDto {

    @NotNull(groups = OnEditSkills.class, message = "Skill Id is required")
    private Long skillId;

    @NotBlank(message = "Skill name can not be empty.")
    private String skillName;

    @Min(value = 1, message = "Value can not be lower than 1")
    @Max(value = 5, message = "Value can not be bigger than 5")
    private Integer skillProficiency;

}
