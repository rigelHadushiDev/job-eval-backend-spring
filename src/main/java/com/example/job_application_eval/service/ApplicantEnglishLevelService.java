package com.example.job_application_eval.service;


import com.example.job_application_eval.dtos.ApplicantEnglishLevelDto;
import com.example.job_application_eval.entities.ApplicantEnglishLevelEntity;

import java.util.List;

public interface ApplicantEnglishLevelService {

    ApplicantEnglishLevelDto deleteApplicantEnglishLevel(Long applicantEnglishLevelId);

    ApplicantEnglishLevelDto findApplicantEnglishLevelByUserId(Long userId);

    ApplicantEnglishLevelDto editApplicantEnglishLevel(ApplicantEnglishLevelDto applicantEnglishLevelDto);

    ApplicantEnglishLevelDto save(ApplicantEnglishLevelDto applicantEnglishLevelDto);

    ApplicantEnglishLevelEntity findApplicantEnglishLevelById(Long applicantEnglishLevelId);
}

