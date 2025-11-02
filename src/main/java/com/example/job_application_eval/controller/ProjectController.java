package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.ProjectDto;

import com.example.job_application_eval.service.ProjectService;
import com.example.job_application_eval.validation.OnEditProject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project")
@RequiredArgsConstructor
public class ProjectController{

    private final ProjectService projectService;

    @DeleteMapping()
    public ResponseEntity<ProjectDto> deleteUserLan(@RequestParam Long projectId) {
        ProjectDto deletedProject = projectService.deleteProjectId(projectId);
        return ResponseEntity.ok(deletedProject);
    }

    @GetMapping("/userProjects")
    public ResponseEntity<List<ProjectDto>> findProjectByUserId(@RequestParam Long userId) {
        List<ProjectDto> projectDtos = projectService.findProjectsByUserId(userId);
        return ResponseEntity.ok(projectDtos);
    }

    @GetMapping("/getProject")
    public ResponseEntity<ProjectDto> getProjectById(@RequestParam Long projectId) {
        ProjectDto extractedProjects = projectService.findProjectById(projectId);
        return ResponseEntity.ok(extractedProjects);
    }

    @PostMapping("create")
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto projectDto) {
        ProjectDto savedProject = projectService.save(projectDto);
        return ResponseEntity.ok(savedProject);
    }

    @PutMapping("edit")
    public ResponseEntity<ProjectDto> editProject(@Validated(OnEditProject.class) @RequestBody ProjectDto projectDto) {
        ProjectDto updatedProject = projectService.editProject(projectDto);
        return ResponseEntity.ok(updatedProject);
    }
}