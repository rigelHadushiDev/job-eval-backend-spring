package com.example.job_application_eval.service;

import com.example.job_application_eval.entities.EducationEntity;
import com.example.job_application_eval.entities.WorkExperienceEntity;

import java.util.List;

public interface WorkExperienceService {


    WorkExperienceEntity deleteWorkExperience(Long workExperienceId);

    List<WorkExperienceEntity> findWorkExperiencesByUserId(Long userId);

    WorkExperienceEntity editWorkExperience(WorkExperienceEntity workExperienceEntity);

    WorkExperienceEntity save(WorkExperienceEntity workExperienceEntity);

    WorkExperienceEntity findWorkExperienceById(Long workExperienceId);
}
