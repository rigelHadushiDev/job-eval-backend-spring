package com.example.job_application_eval.entities;


import com.example.job_application_eval.entities.enums.ProficiencyLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "applicant_language")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantLanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="applicant_language_id")
    private Long applicantLanguageId;

    @Column(nullable = false, name= "language_name")
    private String languageName;

    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency_level", nullable = false)
    private ProficiencyLevel proficiencyLevel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;
}