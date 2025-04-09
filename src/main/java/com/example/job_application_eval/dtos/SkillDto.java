package com.example.job_application_eval.dtos;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillDto {

    private Long id;

    @NotBlank(message = "Skill name can not be empty.")
    private String skillName;

    @Min(1)
    @Max(5)
    private Integer skillProficiency;

}
