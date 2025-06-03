package com.example.job_application_eval.dtos;


import com.example.job_application_eval.validation.OnEditProject;
import com.example.job_application_eval.validation.OnEditSkills;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {

    @NotNull(groups = OnEditProject.class, message = "Project Id is required")
    private Long projectId;

    @NotBlank(groups = {OnEditProject.class, Default.class}, message = "Project title can not be empty.")
    private String projectTitle;

    @NotBlank(groups = {OnEditProject.class, Default.class}, message = "Project description can not be empty.")
    private String description;

    @NotBlank(groups = {OnEditProject.class, Default.class}, message = "Link showcasing the project can not be empty.")
    private String link;

    @NotBlank(groups = {OnEditProject.class, Default.class}, message = "Link showcasing the project can not be empty.")
    private String technologiesOrTools;

    private Boolean finished;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date startDate;

    private Long userId;
}
