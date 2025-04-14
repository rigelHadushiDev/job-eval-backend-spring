package com.example.job_application_eval.service;

import com.example.job_application_eval.entities.EducationEntity;
import com.example.job_application_eval.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EducationService {

    EducationEntity deleteEducation(Long educationId);

    List<EducationEntity> findEducationsByUserId(Long userId);

    EducationEntity editEducation(EducationEntity educationEntity);

    EducationEntity save(EducationEntity educationEntity);

    EducationEntity findEducationById(Long educationId);
}
