package com.example.job_application_eval.service;

import com.example.job_application_eval.entities.ProjectEntity;

import java.util.List;

public interface ProjectService {

    ProjectEntity deleteProjectId(Long userLanguageId);

    List<ProjectEntity> findProjectsByUserId(Long userId);

    ProjectEntity editProject(ProjectEntity projectEntity);

    ProjectEntity save(ProjectEntity projectEntity);

    ProjectEntity findProjectById(Long projectId);
}