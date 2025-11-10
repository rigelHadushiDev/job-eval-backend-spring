package com.example.job_application_eval.repository;

import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.entities.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplicationEntity, Long>, JpaSpecificationExecutor<JobApplicationEntity> {

    Page<JobApplicationEntity> findByUser_UserId(Long userId, Pageable pageable);

    JobApplicationEntity findByUser_UserIdAndJobPosting_JobPostingId(
            Long userId,
            Long jobPostingId
    );
}
