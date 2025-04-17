package com.example.job_application_eval.entities;


import com.example.job_application_eval.config.utils.ProficiencyLevelConverter;
import com.example.job_application_eval.entities.enums.ProficiencyLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "applicant_english_level")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantEnglishLevelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="applicant_english_level_id")
    private Long applicantEnglishLevelId;

    @Convert(converter = ProficiencyLevelConverter.class)
    @Column(name = "proficiency_level", nullable = false)
    private ProficiencyLevel proficiencyLevel;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;
}