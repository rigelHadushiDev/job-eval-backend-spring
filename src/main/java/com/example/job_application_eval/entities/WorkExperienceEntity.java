package com.example.job_application_eval.entities;

import com.example.job_application_eval.entities.enums.EmploymentType;
import com.example.job_application_eval.validation.OnEditWorkExp;
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

@Entity
@Table(name = "work_experience")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_experience_id")
    @NotNull(groups = OnEditWorkExp.class, message = "Work Experience id is required")
    private Long workExperienceId;

    @Column(nullable = false, name = "job_title")
    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @Column(nullable = false, name = "company_name")
    @NotBlank(message = "Company name is required")
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "employment_type")
    @NotNull(message = "Employment type is required")
    private EmploymentType employmentType;

    @Column(nullable = false, name = "start_date")
    @NotNull(message = "Start Date is required")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "total_years")
    private Double totalYears;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;
}
