package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.dtos.ProjectDto;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.ProjectEntity;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.repository.ProjectRepository;

import com.example.job_application_eval.service.ProjectService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final Utils utils;
    private final Mapper<ProjectEntity, ProjectDto> mapper;

    @Override
    public ProjectDto deleteProjectId(Long projectId) {
        ProjectDto currentProjectDto = findProjectById(projectId);
        ProjectEntity currentProjectEntity = mapper.mapFrom(currentProjectDto);
        utils.assertCurrentUserOwns(currentProjectEntity.getUser().getUserId());
        repository.deleteById(projectId);
        return currentProjectDto;
    }

    @Override
    public List<ProjectDto> findProjectsByUserId(Long userId) {
        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(userId) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "notAuthorizedToViewTheseProjects");
        }
        List<ProjectEntity> entities = repository.findByUser_UserId(userId);
        if (entities == null || entities.isEmpty()) return List.of();

        return entities.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto editProject(ProjectDto projectDto) {

        ProjectEntity projectEntity = mapper.mapFrom(projectDto);
        ProjectEntity currentProject = Optional
                .ofNullable(repository.findByProjectId(projectEntity.getProjectId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "projectNotFound"));

        utils.assertCurrentUserOwns(currentProject.getUser().getUserId());
        utils.validateAndUpdateProject(currentProject);
        projectEntity.setUser(currentProject.getUser());

        ProjectEntity editedProjectEntity =  repository.save(projectEntity);
        return mapper.mapTo(editedProjectEntity);
    }

    @Override
    public ProjectDto save(ProjectDto ProjectDto) {

        ProjectEntity projectEntity = mapper.mapFrom(ProjectDto);
        UserEntity currentUser = utils.getCurrentUser();
        utils.validateAndUpdateProject(projectEntity);
        projectEntity.setUser(currentUser);
        ProjectEntity savedProject =  repository.save(projectEntity);
        return mapper.mapTo(savedProject);
    }

    @Override
    public ProjectDto findProjectById(Long projectId) {

        ProjectEntity projectEntity =  Optional
                .ofNullable(repository.findByProjectId(projectId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "projectNotFound"));

        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(projectEntity.getUser().getUserId()) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "notAuthorizedToViewThisProject");
        }
        return mapper.mapTo(projectEntity);
    }

}
