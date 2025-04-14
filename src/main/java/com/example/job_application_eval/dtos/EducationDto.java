package com.example.job_application_eval.dtos;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.enums.EducationLevel;
import com.example.job_application_eval.validation.OnEditEducation;
import com.example.job_application_eval.validation.OnEditUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationDto {

    @NotNull(groups = {OnEditEducation.class}, message=  "Education Id is required")
    private Long educationId;

    @NotNull(message = "Education level is required")
    private EducationLevel educationLevel;

    private String fieldOfStudy;

    @NotBlank(message = "Institution name is required")
    private String institution;

    @NotNull(message = "Finished field must be specified")
    private Boolean finished;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date graduationDate;

    private String achievementsDescription;
}
