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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("jobApplication")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;
    private final Mapper<JobApplicationEntity, JobApplicationDto> userMapper;
    private final Mapper<JobApplicationEntity, JobApplicationHighRoleDto> highRolemapper;


    @PostMapping("apply")
    public JobApplicationDto apply(@RequestParam("jobPostingId") Long jobPostingId) {
        JobApplicationEntity jobApplicationEntity = jobApplicationService.apply(jobPostingId);
        return userMapper.mapTo(jobApplicationEntity);
    }

    @GetMapping("/myApplicationFilter")
    public Page<JobApplicationDto> getFilteredJobApplications(
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) Long jobPostingId,
            @RequestParam(required = false) LocalDateTime applicationDate,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String orderBy,
            Pageable pageable) {

        return jobApplicationService.filterMyJobApplications( status, jobPostingId, applicationDate,sortBy,orderBy, pageable)
                .map(userMapper::mapTo);
    }

    @GetMapping("/anyApplicationFilter")
    public Page<JobApplicationHighRoleDto> getFilteredJobApplications(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) Long jobPostingId,
            @RequestParam(required = false) LocalDateTime applicationDate,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String orderBy,
            Pageable pageable) {

        return jobApplicationService.filterAnyJobApplications(userId, status, jobPostingId, applicationDate, sortBy,orderBy, pageable)
                .map(highRolemapper::mapTo);
    }

    @PatchMapping("changeStatus")
    public JobApplicationDto changeStatus(@RequestParam("jobApplicationId") Long jobApplicationId,@RequestParam("status") ApplicationStatus status) {
        JobApplicationEntity jobApplicationEntity = jobApplicationService.changeStatus(jobApplicationId, status);
        return userMapper.mapTo(jobApplicationEntity);
    }
}
