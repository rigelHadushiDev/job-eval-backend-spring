package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.ProjectDto;
import com.example.job_application_eval.entities.ProjectEntity;
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

    @DeleteMapping()
    public ResponseEntity<ProjectDto> deleteUserLan(@RequestParam Long projectId) {
        ProjectEntity deletedProject = projectService.deleteProjectId(projectId);
        return new ResponseEntity<>(mapper.mapTo(deletedProject), HttpStatus.OK);
    }

    @GetMapping("/userProjects")
    public ResponseEntity<List<ProjectDto>> findProjectByUserId(@RequestParam Long userId) {
        List<ProjectEntity> projectEntities = projectService.findProjectsByUserId(userId);
        List<ProjectDto> projects = projectEntities.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/getProject")
    public ResponseEntity<ProjectDto> getProjectById(@RequestParam Long projectId) {
        ProjectEntity extractedProject = projectService.findProjectById(projectId);
        return new ResponseEntity<>(mapper.mapTo(extractedProject), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto projectDto) {
        ProjectEntity projectEntity = mapper.mapFrom(projectDto);
        ProjectEntity updatedProject = projectService.save(projectEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedProject), HttpStatus.OK);
    }

    @PutMapping("edit")
    public ResponseEntity<ProjectDto> editProject(@Validated(OnEditProject.class) @RequestBody ProjectDto projectDto) {
        ProjectEntity projectEntity = mapper.mapFrom(projectDto);
        ProjectEntity updatedProject = projectService.editProject(projectEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedProject), HttpStatus.OK);
    }
}