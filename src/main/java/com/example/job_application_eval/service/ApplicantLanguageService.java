package com.example.job_application_eval.service;


import com.example.job_application_eval.entities.ApplicantLanguageEntity;

import java.util.List;

public interface ApplicantLanguageService {

    ApplicantLanguageEntity deleteApplicantLanguage(Long userLanguageId);

    List<ApplicantLanguageEntity> findApplicantLanguagesByUserId(Long userId);

    ApplicantLanguageEntity editApplicantLanguage(ApplicantLanguageEntity applicantLanguageEntity);

    ApplicantLanguageEntity save(ApplicantLanguageEntity applicantLanguageEntity);

    ApplicantLanguageEntity findApplicantLanguageById(Long userLanguageId);
}

