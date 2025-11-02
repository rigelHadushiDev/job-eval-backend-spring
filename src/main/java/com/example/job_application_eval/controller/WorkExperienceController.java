package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.WorkExperienceDto;
import com.example.job_application_eval.dtos.WorkExperienceDto;
import com.example.job_application_eval.entities.WorkExperienceEntity;
import com.example.job_application_eval.entities.WorkExperienceEntity;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.service.WorkExperienceService;
import com.example.job_application_eval.validation.OnEditWorkExp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("workExp")
@RequiredArgsConstructor
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @DeleteMapping()
    public ResponseEntity<WorkExperienceDto> deleteWorkExperience(@RequestParam Long workExperienceId) {
        WorkExperienceDto deletedWorkExperience = workExperienceService.deleteWorkExperience(workExperienceId);
        return ResponseEntity.ok(deletedWorkExperience);
    }

    @GetMapping("/userWorkExperiences")
    public ResponseEntity<List<WorkExperienceDto>> findWorkExperienceByUserId(@RequestParam Long userId) {
        List<WorkExperienceDto> workExperienceDtos = workExperienceService.findWorkExperiencesByUserId(userId);
        return ResponseEntity.ok(workExperienceDtos);
    }

    @GetMapping("/getWorkExperience")
    public ResponseEntity<WorkExperienceDto> getWorkExperienceById(@RequestParam Long workExperienceId) {
        WorkExperienceDto extractedWorkExperience = workExperienceService.findWorkExperienceById(workExperienceId);
        return ResponseEntity.ok(extractedWorkExperience);
    }


    @PostMapping("create")
    public ResponseEntity<WorkExperienceDto> createWorkExperience(@Valid  @RequestBody WorkExperienceDto workExperienceDto) {
        WorkExperienceDto savedWorkExperience = workExperienceService.save(workExperienceDto);
        return ResponseEntity.ok(savedWorkExperience);
    }

    @PutMapping("edit")
    public ResponseEntity<WorkExperienceDto> editWorkExperience(@Validated(OnEditWorkExp.class) @RequestBody WorkExperienceDto workExperienceDto) {
        WorkExperienceDto updatedWorkExperience = workExperienceService.editWorkExperience(workExperienceDto);
        return ResponseEntity.ok(updatedWorkExperience);
    }
}