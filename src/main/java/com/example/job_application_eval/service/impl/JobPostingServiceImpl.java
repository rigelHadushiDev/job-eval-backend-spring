package com.example.job_application_eval.service.impl;


import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.entities.JobPostingEntity;
import com.example.job_application_eval.repository.JobPostingRepository;
import com.example.job_application_eval.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class JobPostingServiceImpl  implements JobPostingService {

    private final Utils utils;
    private final JobPostingRepository jobPostingRepository;

    @Override
    public JobPostingEntity save(JobPostingEntity jobPostingEntity) {
        return jobPostingRepository.save(jobPostingEntity);
    }

    @Override
    public JobPostingEntity findById(long jobPostingId) {
        return jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Posting not found"));
    }

    @Override
    public Page<JobPostingEntity> findAll(Pageable pageable, Boolean closed) {
        return null;
    }

    @Override
    public JobPostingEntity edit(JobPostingEntity jobPostingEntity) {
        return null;
    }

    @Override
    public JobPostingEntity delete(long jobPostingId) {
        return null;
    }

    @Override
    public Page<JobPostingEntity> findByJobTitle(String jobTitle, Pageable pageable) {
        return null;
    }
}
