package com.example.job_application_eval.entities;

import com.example.job_application_eval.entities.enums.EducationLevel;
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
@Table(name = "education")
public class EducationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Long educationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", nullable = false)
    private EducationLevel educationLevel;

    @Column( name= "field_of_study", nullable = false)
    private String fieldOfStudy;

    @Column(nullable = false)
    private String institution;

    @Column(name = "started_date")
    private Date startedDate;

    @Column
    private Boolean finished;

    @Column(name = "graduation_date")
    private Date graduationDate;

    @Column(name = "achievements_description",columnDefinition = "TEXT")
    private String achievementsDescription;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;
}
