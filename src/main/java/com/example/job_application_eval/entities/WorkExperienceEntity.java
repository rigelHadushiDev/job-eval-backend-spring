package com.example.job_application_eval.entities;

import com.example.job_application_eval.entities.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "work_experience")
public class WorkExperienceEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_experience_id")
    private Long workExperienceId;

    @Column(nullable = false, name = "job_title" )
    private String jobTitle;

    @Column(nullable = false, name= "companyName" )
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false, name = "employment_type")
    private EmploymentType employmentType;

    @Column( nullable = false, name = "start_date")
    private Date startDate;

    @Column( nullable = false, name = "end_date")
    private Date endDate;

    @Column
    private String description;

    @Column(name = "total_years")
    private Double totalYears;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

}
