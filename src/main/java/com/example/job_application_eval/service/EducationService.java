package com.example.job_application_eval.service;

import com.example.job_application_eval.dtos.EducationDto;
import com.example.job_application_eval.entities.EducationEntity;
import com.example.job_application_eval.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EducationService {

    EducationDto deleteEducation(Long educationId);

    List<EducationDto> findEducationsByUserId(Long userId);

    EducationDto editEducation(EducationDto educationDto);

    EducationDto save(EducationDto educationDto);

    EducationDto findEducationById(Long educationId);
}
