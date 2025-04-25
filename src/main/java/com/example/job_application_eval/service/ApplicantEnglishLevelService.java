package com.example.job_application_eval.service;


import com.example.job_application_eval.entities.ApplicantEnglishLevelEntity;

import java.util.List;

public interface ApplicantEnglishLevelService {

    ApplicantEnglishLevelEntity deleteApplicantEnglishLevel(Long applicantEnglishLevelId);

    ApplicantEnglishLevelEntity findApplicantEnglishLevelByUserId(Long userId);

    ApplicantEnglishLevelEntity editApplicantEnglishLevel(ApplicantEnglishLevelEntity applicantEnglishLevelEntity);

    ApplicantEnglishLevelEntity save(ApplicantEnglishLevelEntity applicantEnglishLevelEntity);

    ApplicantEnglishLevelEntity findApplicantEnglishLevelById(Long applicantEnglishLevelId);
}

