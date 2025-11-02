package com.example.job_application_eval.service;

import com.example.job_application_eval.dtos.WorkExperienceDto;

import java.util.List;

public interface WorkExperienceService {


    WorkExperienceDto deleteWorkExperience(Long workExperienceId);

    List<WorkExperienceDto> findWorkExperiencesByUserId(Long userId);

    WorkExperienceDto editWorkExperience(WorkExperienceDto WorkExperienceDto);

    WorkExperienceDto save(WorkExperienceDto WorkExperienceDto);

    WorkExperienceDto findWorkExperienceById(Long workExperienceId);
}
