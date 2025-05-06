package com.example.job_application_eval.entities;


import com.example.job_application_eval.entities.enums.ApplicationStatus;
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
@Table(name = "job_application")
public class JobApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_application_id")
    private Long jobApplicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPostingEntity jobPosting;

    @Column(name = "application_date", nullable = false)
    private LocalDateTime applicationDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(name = "general_score")
    private Double generalScore;

    @Column(name = "education_score")
    private Double educationScore;

    @Column(name = "english_score")
    private Double englishScore;

    @Column(name = "skills_score")
    private Double skillsScore;

    @Column(name = "experience_years_score")
    private Double experienceYearsScore;

    @Column(name =  "experience_similarity_score")
    private Double experienceSimilarityScore;

    @PrePersist
    public void prePersist() {
        if (this.applicationDate == null) {
            this.applicationDate = LocalDateTime.now();
        }
        if (user != null) {
            this.username = user.getUsername();
        }
    }
}