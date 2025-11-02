package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.JobApplicationDto;
import com.example.job_application_eval.dtos.JobApplicationHighRoleDto;
import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.entities.enums.ApplicationStatus;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("jobApplication")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping("apply")
    public ResponseEntity<JobApplicationDto> apply(@RequestParam("jobPostingId") Long jobPostingId) {
        JobApplicationDto jobApplicationDto = jobApplicationService.apply(jobPostingId);
        return ResponseEntity.ok(jobApplicationDto);
    }

    @GetMapping("/myApplicationFilter")
    public ResponseEntity<Page<JobApplicationDto>> getFilteredJobApplications(
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) Long jobPostingId,
            @RequestParam(required = false) LocalDateTime applicationDate,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) Boolean closed,
            @RequestParam(required = false) Long jobApplicationId,
            Pageable pageable) {


        Page<JobApplicationDto> jobApplications = jobApplicationService.filterMyJobApplications( status, jobPostingId, applicationDate,sortBy,orderBy,fullName, jobTitle,closed,jobApplicationId ,pageable);
        return ResponseEntity.ok(jobApplications);
    }

    @GetMapping("/anyApplicationFilter")
    public ResponseEntity<Page<JobApplicationHighRoleDto>> getFilteredJobApplications(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) Long jobPostingId,
            @RequestParam(required = false) LocalDateTime applicationDate,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) Boolean closed,
            @RequestParam(required = false) Long jobApplicationId,
            Pageable pageable) {

        Page<JobApplicationHighRoleDto> highRoleJobApplication =  jobApplicationService.filterAnyJobApplications(userId, status, jobPostingId, applicationDate, sortBy,orderBy, fullName,jobTitle,closed ,jobApplicationId, pageable);
        return ResponseEntity.ok(highRoleJobApplication);

    }

    @PatchMapping("changeStatus")
    public ResponseEntity<JobApplicationDto> changeStatus(@RequestParam("jobApplicationId") Long jobApplicationId,@RequestParam("status") ApplicationStatus status) {
        JobApplicationDto jobApplicationDto = jobApplicationService.changeStatus(jobApplicationId, status);
        return ResponseEntity.ok(jobApplicationDto);
    }
}
