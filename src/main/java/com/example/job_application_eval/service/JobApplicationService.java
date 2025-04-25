package com.example.job_application_eval.service;

import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.entities.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobApplicationService {

    JobApplicationEntity apply(Long jobPostingId);

    JobApplicationEntity changeStatus(Long jobApplicationId, ApplicationStatus status);

    Page<JobApplicationEntity> getJobApplicationsByStatus(ApplicationStatus status, Pageable pageable);

    Page<JobApplicationEntity> getJobApplicationsByJobPostingId(Long jobPostingId, Pageable pageable);

    Page<JobApplicationEntity> getByStatusAndJobPostingId(
            ApplicationStatus status,
            Long jobPostingId,
            Pageable pageable
    );
    Page<JobApplicationEntity> getJobApplicationsByUserId(Long userId, Pageable pageable);


    JobApplicationEntity getByJobApplicationId(Long jobApplicationId);

}
