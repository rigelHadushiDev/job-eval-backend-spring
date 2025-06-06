package com.example.job_application_eval.service;

import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.entities.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface JobApplicationService {



    JobApplicationEntity apply(Long jobPostingId);

    JobApplicationEntity changeStatus(Long jobApplicationId, ApplicationStatus status);

    Page<JobApplicationEntity> filterMyJobApplications(ApplicationStatus status, Long jobPostingId,
                                                      LocalDateTime applicationDate, String sortBy, String orderType,String fullName, String jobTitle, Boolean closed,
             Long jobApplicationId , Pageable pageable);

    Page<JobApplicationEntity> filterAnyJobApplications(Long userId, ApplicationStatus status, Long jobPostingId,
                                                     LocalDateTime applicationDate, String sortBy, String orderType,String fullName, String jobTitle, Boolean closed,
                                                        Long jobApplicationId, Pageable pageable);


    JobApplicationEntity getByJobApplicationId(Long jobApplicationId);

}
