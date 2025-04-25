package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.JobApplicationDto;
import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.entities.enums.ApplicationStatus;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("jobApplication")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;
    private final Mapper<JobApplicationEntity, JobApplicationDto> mapper;

    // USER
    @PostMapping("apply")
    public JobApplicationDto apply(@RequestParam("jobPostingId") Long jobPostingId) {
        JobApplicationEntity jobApplicationEntity = jobApplicationService.apply(jobPostingId);
        return mapper.mapTo(jobApplicationEntity);
    }

    // ADMIN, Recruiter, USER
    @GetMapping("getByUserId")
    public Page<JobApplicationDto> getJobApplicationsByUserId(@RequestParam("userId") Long userId, Pageable pageable) {
        return jobApplicationService.getJobApplicationsByUserId(userId, pageable)
                .map(mapper::mapTo);
    }

    // ADMIN, Recruiter
    @GetMapping("getByStatus")
    public Page<JobApplicationDto> getJobApplicationsByStatus(@RequestParam("status") ApplicationStatus status, Pageable pageable) {
        return jobApplicationService.getJobApplicationsByStatus(status, pageable)
                .map(mapper::mapTo);
    }

    // ADMIN, Recruiter
    @GetMapping("getByJobPostingId")
    public Page<JobApplicationDto> getJobApplicationsByJobPostingId(@RequestParam("jobPostingId") Long jobPostingId, Pageable pageable) {
        return jobApplicationService.getJobApplicationsByJobPostingId(jobPostingId, pageable)
                .map(mapper::mapTo);
    }

    // ADMIN, Recruiter
    @GetMapping("getByJobPostingIdAndStatus")
    public Page<JobApplicationDto> getJobApplicationsByJobPostingIdAndStatus(@RequestParam("jobPostingId") Long jobPostingId,
                                                                             @RequestParam("status") ApplicationStatus status,
                                                                             Pageable pageable) {
        return jobApplicationService.getByStatusAndJobPostingId( status,jobPostingId, pageable)
                .map(mapper::mapTo);
    }

    // ADMIN, Recruiter
    @PatchMapping("changeStatus")
    public JobApplicationDto changeStatus(@RequestParam("jobApplicationId") Long jobApplicationId,@RequestParam("status") ApplicationStatus status) {
        JobApplicationEntity jobApplicationEntity = jobApplicationService.changeStatus(jobApplicationId, status);
        return mapper.mapTo(jobApplicationEntity);
    }
}
