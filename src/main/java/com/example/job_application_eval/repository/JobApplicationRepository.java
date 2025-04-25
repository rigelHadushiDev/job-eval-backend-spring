package com.example.job_application_eval.repository;

import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.entities.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplicationEntity, Long> {

    Page<JobApplicationEntity> findByStatus(ApplicationStatus status, Pageable pageable);

    Page<JobApplicationEntity> findByJobPosting_JobPostingId(Long jobPostingId, Pageable pageable);

    Page<JobApplicationEntity> findByStatusAndJobPosting_JobPostingId(
            ApplicationStatus status,
            Long jobPostingId,
            Pageable pageable
    );

    Page<JobApplicationEntity> findByUser_UserId(Long userId, Pageable pageable);
}
