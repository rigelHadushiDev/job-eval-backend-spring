package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.ProjectEntity;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.repository.ProjectRepository;

import com.example.job_application_eval.service.ProjectService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final Utils utils;

    @Override
    public ProjectEntity deleteProjectId(Long projectId) {
        ProjectEntity currentProject = findProjectById(projectId);
        utils.assertCurrentUserOwns(currentProject.getUser().getUserId());
        repository.deleteById(projectId);
        return currentProject;
    }

    @Override
    public List<ProjectEntity> findProjectsByUserId(Long userId) {
        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(userId) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view these projects");
        }
        return repository.findByUser_UserId(userId);
    }

    @Override
    public ProjectEntity editProject(ProjectEntity projectEntity) {
        ProjectEntity currentExperience = repository.findByProjectId(projectEntity.getProjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        utils.assertCurrentUserOwns(currentExperience.getUser().getUserId());
        projectEntity.setUser(currentExperience.getUser());
        return repository.save(projectEntity);
    }

    @Override
    public ProjectEntity save(ProjectEntity projectEntity) {
        UserEntity currentUser = utils.getCurrentUser();
        projectEntity.setUser(currentUser);
        return repository.save(projectEntity);
    }

    @Override
    public ProjectEntity findProjectById(Long projectId) {
        ProjectEntity projectEntity =  repository.findByProjectId(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found"));

        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(projectEntity.getUser().getUserId()) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this project");
        }
        return projectEntity;
    }

}
