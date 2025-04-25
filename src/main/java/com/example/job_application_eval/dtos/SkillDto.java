package com.example.job_application_eval.dtos;


import com.example.job_application_eval.validation.OnEditSkills;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.internal.bytebuddy.pool.TypePool;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillDto {

    @NotNull(groups = OnEditSkills.class, message = "Skill Id is required")
    private Long skillId;

    @NotBlank(groups = {OnEditSkills.class, Default.class}, message = "Skill name can not be empty.")
    private String skillName;

    @Min(value = 1, message = "Value can not be lower than 1")
    @Max(value = 5, message = "Value can not be bigger than 5")
    @NotNull(groups = {OnEditSkills.class, Default.class}, message = "Skill Proficiency is required")
    private Integer skillProficiency;

    private Long userId;
}
