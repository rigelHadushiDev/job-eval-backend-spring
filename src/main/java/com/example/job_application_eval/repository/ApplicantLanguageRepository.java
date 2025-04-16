package com.example.job_application_eval.repository;

import com.example.job_application_eval.entities.ApplicantLanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicantLanguageRepository extends JpaRepository<ApplicantLanguageEntity, Long> {

    List<ApplicantLanguageEntity> findByUser_UserId(Long userId);

    Optional<ApplicantLanguageEntity> findByApplicantLanguageId(Long applicantLanguageId);

    ApplicantLanguageEntity save(ApplicantLanguageEntity applicantLanguageEntity);

}