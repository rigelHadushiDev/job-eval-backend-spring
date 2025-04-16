package com.example.job_application_eval.controller;


import com.example.job_application_eval.dtos.ProjectDto;
import com.example.job_application_eval.entities.ProjectEntity;
import com.example.job_application_eval.entities.WorkExperienceEntity;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.service.ProjectService;
import com.example.job_application_eval.validation.OnEditProject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("project")
@RequiredArgsConstructor
public class ProjectController{

    private final ProjectService projectService;
    private final Mapper<ProjectEntity, ProjectDto> mapper;


    // USER
    @DeleteMapping()
    public ResponseEntity<ProjectDto> deleteProject(@RequestParam Long projectId) {
        ProjectEntity deletedProject = projectService.deleteProjectId(projectId);
        return new ResponseEntity<>(mapper.mapTo(deletedProject), HttpStatus.OK);
    }

    // USER, ADMIN, Recruiter
    @GetMapping("/userProjects")
    public ResponseEntity<List<ProjectDto>> findWorkExperienceByUserId(@RequestParam Long userId) {
        List<ProjectEntity> projectEntities = projectService.findProjectsByUserId(userId);
        List<ProjectDto> projects = projectEntities.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // USER, ADMIN, REcruiter
    @GetMapping("/getProject")
    public ResponseEntity<ProjectDto> getWorkExperienceById(@RequestParam Long projectId) {
        ProjectEntity extractedProject = projectService.findProjectById(projectId);
        return new ResponseEntity<>(mapper.mapTo(extractedProject), HttpStatus.OK);
    }

// USER
    @PostMapping("create")
    public ResponseEntity<ProjectDto> createWorkExperience(@Valid @RequestBody ProjectDto projectDto) {
        ProjectEntity projectEntity = mapper.mapFrom(projectDto);
        ProjectEntity updatedProject = projectService.save(projectEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedProject), HttpStatus.OK);
    }

    // USER
    @PutMapping("edit")
    public ResponseEntity<ProjectDto> editWorkExperience(@Validated(OnEditProject.class) @RequestBody ProjectDto projectDto) {
        ProjectEntity projectEntity = mapper.mapFrom(projectDto);
        ProjectEntity updatedProject = projectService.editProject(projectEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedProject), HttpStatus.OK);
    }
}