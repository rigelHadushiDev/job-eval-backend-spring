package com.example.job_application_eval.service;

import com.example.job_application_eval.dtos.ProjectDto;
import com.example.job_application_eval.entities.ProjectEntity;

import java.util.List;

public interface ProjectService {

    ProjectDto deleteProjectId(Long userLanguageId);

    List<ProjectDto> findProjectsByUserId(Long userId);

    ProjectDto editProject(ProjectDto projectDto);

    ProjectDto save(ProjectDto projectDto);

    ProjectDto findProjectById(Long projectId);
}