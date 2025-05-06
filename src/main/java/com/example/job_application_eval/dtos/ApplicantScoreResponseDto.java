package com.example.job_application_eval.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantScoreResponseDto {
    @JsonProperty("experience_similarity_score")
    private double experienceSimilarityScore;

    @JsonProperty("experience_years_score")
    private double experienceYearsScore;

    @JsonProperty("education_score")
    private double educationScore;

    @JsonProperty("english_score")
    private double englishScore;

    @JsonProperty("skill_score")
    private double skillScore;

    @JsonProperty("final_score")
    private double finalScore;
}
