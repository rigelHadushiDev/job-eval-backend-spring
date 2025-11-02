package com.example.job_application_eval.service;

import com.example.job_application_eval.dtos.JobApplicationDto;
import com.example.job_application_eval.dtos.JobApplicationHighRoleDto;
import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.entities.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface JobApplicationService {



    JobApplicationDto apply(Long jobPostingId);

    JobApplicationDto changeStatus(Long jobApplicationId, ApplicationStatus status);

    Page<JobApplicationDto> filterMyJobApplications(ApplicationStatus status, Long jobPostingId,
                                                      LocalDateTime applicationDate, String sortBy, String orderType,String fullName, String jobTitle, Boolean closed,
             Long jobApplicationId , Pageable pageable);

    Page<JobApplicationHighRoleDto> filterAnyJobApplications(Long userId, ApplicationStatus status, Long jobPostingId,
                                                             LocalDateTime applicationDate, String sortBy, String orderType, String fullName, String jobTitle, Boolean closed,
                                                             Long jobApplicationId, Pageable pageable);

    JobApplicationDto getByJobApplicationId(Long jobApplicationId);

}
