package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.WorkExperienceDto;
import com.example.job_application_eval.dtos.WorkExperienceDto;
import com.example.job_application_eval.entities.WorkExperienceEntity;
import com.example.job_application_eval.entities.WorkExperienceEntity;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.service.WorkExperienceService;
import com.example.job_application_eval.validation.OnEditWorkExp;
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
    private final Mapper<WorkExperienceEntity, WorkExperienceDto> mapper;


    @DeleteMapping()
    public ResponseEntity<WorkExperienceDto> deleteWorkExperience(@RequestParam Long workExperienceId) {
        WorkExperienceEntity deletedWorkExperience = workExperienceService.deleteWorkExperience(workExperienceId);
        return new ResponseEntity<>(mapper.mapTo(deletedWorkExperience), HttpStatus.OK);
    }

    @GetMapping("/userWorkExperiences")
    public ResponseEntity<List<WorkExperienceDto>> findWorkExperienceByUserId(@RequestParam Long userId) {
        List<WorkExperienceEntity> workExperienceEntities = workExperienceService.findWorkExperiencesByUserId(userId);
        List<WorkExperienceDto> workExperienceDto = workExperienceEntities.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(workExperienceDto, HttpStatus.OK);
    }

    @GetMapping("/getWorkExperience")
    public ResponseEntity<WorkExperienceDto> getWorkExperienceById(@RequestParam Long workExperienceId) {
        WorkExperienceEntity extractedWorkExperience = workExperienceService.findWorkExperienceById(workExperienceId);
        return new ResponseEntity<>(mapper.mapTo(extractedWorkExperience), HttpStatus.OK);
    }


    @PostMapping("create")
    public ResponseEntity<WorkExperienceDto> createWorkExperience(@RequestBody WorkExperienceDto WorkExperienceDto) {
        WorkExperienceEntity WorkExperienceEntity = mapper.mapFrom(WorkExperienceDto);
        WorkExperienceEntity updatedWorkExperience = workExperienceService.save(WorkExperienceEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedWorkExperience), HttpStatus.OK);
    }

    @PutMapping("edit")
    public ResponseEntity<WorkExperienceDto> editWorkExperience(@Validated(OnEditWorkExp.class) @RequestBody WorkExperienceDto WorkExperienceDto) {
        WorkExperienceEntity WorkExperienceEntity = mapper.mapFrom(WorkExperienceDto);
        WorkExperienceEntity updatedWorkExperience = workExperienceService.editWorkExperience(WorkExperienceEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedWorkExperience), HttpStatus.OK);
    }
}