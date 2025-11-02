package com.example.job_application_eval.service;

import com.example.job_application_eval.dtos.JobPostingDto;
import com.example.job_application_eval.entities.JobPostingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobPostingService {

    JobPostingDto save(JobPostingDto jobPostingDto);

    JobPostingDto findById(Long jobPostingId);

    Page<JobPostingDto> findAllJobPostings(Boolean closed, Pageable pageable);

    JobPostingDto edit(JobPostingDto jobPostingDto);

    JobPostingDto delete(Long jobPostingId);

    Page<JobPostingDto> searchByJobTitle(String title, Pageable pageable);
}
