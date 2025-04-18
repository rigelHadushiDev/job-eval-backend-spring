package com.example.job_application_eval.service;

import com.example.job_application_eval.entities.JobPostingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobPostingService {

    JobPostingEntity save(JobPostingEntity jobPostingEntity);

    JobPostingEntity findById(Long jobPostingId);

    Page<JobPostingEntity> findAllJobPostings(Boolean closed, Pageable pageable);

    JobPostingEntity edit(JobPostingEntity jobPostingEntity);

    JobPostingEntity delete(Long jobPostingId);

    Page<JobPostingEntity> searchByJobTitle(String title, Pageable pageable);
}
