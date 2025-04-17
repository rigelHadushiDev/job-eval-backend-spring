package com.example.job_application_eval.entities;


import com.example.job_application_eval.config.utils.ProficiencyLevelConverter;
import com.example.job_application_eval.entities.enums.EducationLevel;
import com.example.job_application_eval.entities.enums.EmploymentType;
import com.example.job_application_eval.entities.enums.ProficiencyLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_posting")
public class JobPostingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_posting_id")
    private Long jobPostingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false)
    private EmploymentType employmentType;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "job_description", nullable = false)
    private String jobDescription;

    @Convert(converter = ProficiencyLevelConverter.class)
    @Column(name = "required_english_level", nullable = false)
    private ProficiencyLevel requiredEnglishLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "required_education_level", nullable = false)
    private EducationLevel requiredEducationLevel;

    @Column(name = "required_experience_years", nullable = false)
    private Integer requiredExperienceYears;

    @Column(name = "required_skills", nullable = false)
    private Integer requiredSkills;

    // need to be added in the Dto
    @Column(name = "closed", nullable = false)
    private boolean closed = false;

    @Column(name = "opened_at", nullable = false, updatable = false)
    private LocalDateTime openedAt = LocalDateTime.now();

    @Column(name = "closedAt", nullable = false)
    private LocalDateTime closedAt;

   // also it should be added createdBy, modified by


    // need to be added in the Dto

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;
}