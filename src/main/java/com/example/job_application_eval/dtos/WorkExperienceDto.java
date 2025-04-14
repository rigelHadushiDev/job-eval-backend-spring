package com.example.job_application_eval.dtos;

import com.example.job_application_eval.entities.enums.EmploymentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkExperienceDto {

    private Long workExperienceId;

    @NotBlank(message = "Job Title can not be empty.")
    private String jobTitle;

    @NotBlank(message = "Company Name can not be empty.")
    private String companyName;

    @NotNull( message = "Employment Type is required.")
    private EmploymentType employmentType;

    @NotNull( message = "Work Experience start date is required.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date endDate;

    private String description;

    private Double totalYears;

    private Long userId;
}
