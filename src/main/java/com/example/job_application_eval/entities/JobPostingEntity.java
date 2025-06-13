package com.example.job_application_eval.entities;


import com.example.job_application_eval.config.utils.ProficiencyLevelConverter;
import com.example.job_application_eval.entities.enums.EducationLevel;
import com.example.job_application_eval.entities.enums.EmploymentType;
import com.example.job_application_eval.entities.enums.ProficiencyLevel;
import com.example.job_application_eval.entities.enums.WorkingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(name = "job_description",columnDefinition = "TEXT", nullable = false)
    private String jobDescription;

    @Convert(converter = ProficiencyLevelConverter.class)
    @Column(name = "required_english_level", nullable = false)
    private ProficiencyLevel requiredEnglishLevel;

    @Column(name = "required_experience_years", nullable = false)
    private Integer requiredExperienceYears;

    @Column(name = "required_skills", nullable = false)
    private String requiredSkills;

    @Column(name = "closed", nullable = false)
    private Boolean closed = false;

    @Column(name = "opened_at", updatable = false)
    private LocalDateTime openedAt = LocalDateTime.now();

    @Column(name = "closedAt")
    private LocalDateTime closedAt;

    @Column(name = "min_salary")
    private Integer minSalary;

    @Column(name = "max_salary")
    private Integer maxSalary;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "working_type", nullable = false)
    private WorkingType workingType;

    @PrePersist
    public void prePersist() {
        if (this.openedAt == null) {
            this.openedAt = LocalDateTime.now();
        }
    }
}