package com.example.job_application_eval.repository;

import com.example.job_application_eval.dtos.JobPostingDto;
import com.example.job_application_eval.entities.JobPostingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPostingEntity, Long> {




}
