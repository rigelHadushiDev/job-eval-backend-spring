package com.example.job_application_eval.service;

import com.example.job_application_eval.entities.JobPostingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobPostingService {

    JobPostingEntity save(JobPostingEntity jobPostingEntity);

    JobPostingEntity findById(long jobPostingId);

    Page<JobPostingEntity> findAll(Pageable pageable, Boolean closed);

    JobPostingEntity edit(JobPostingEntity jobPostingEntity);

    JobPostingEntity delete(long jobPostingId);

    Page<JobPostingEntity> findByJobTitle(String jobTitle, Pageable pageable);
}
