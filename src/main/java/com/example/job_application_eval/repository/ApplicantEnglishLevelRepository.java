package com.example.job_application_eval.repository;

import com.example.job_application_eval.entities.ApplicantEnglishLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicantEnglishLevelRepository extends JpaRepository<ApplicantEnglishLevelEntity, Long> {

    ApplicantEnglishLevelEntity findByUser_UserId(Long userId);

    Optional<ApplicantEnglishLevelEntity> findByApplicantEnglishLevelId(Long applicantEnglishLevelId);

    ApplicantEnglishLevelEntity save(ApplicantEnglishLevelEntity applicantEnglishLevelEntity);

}