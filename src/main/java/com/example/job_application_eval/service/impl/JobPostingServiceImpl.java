package com.example.job_application_eval.service.impl;


import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.entities.JobPostingEntity;
import com.example.job_application_eval.entities.ProjectEntity;
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
    public JobPostingEntity findById(Long jobPostingId) {
        return jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Posting not found"));
    }

    @Override
    public Page<JobPostingEntity> findAllJobPostings(Boolean closed, Pageable pageable) {
        Page<JobPostingEntity> jobEntities;

        if (closed == null) {
            jobEntities = jobPostingRepository.findAll(pageable);
        } else {
            jobEntities = jobPostingRepository.findByClosed(closed, pageable);
        }

        return jobEntities;
    }



    @Override
    public Page<JobPostingEntity> searchByJobTitle(String title, Pageable pageable) {
        return jobPostingRepository.findByJobTitleContainingIgnoreCase(title, pageable);
    }


    @Override
    public JobPostingEntity edit(JobPostingEntity jobPostingEntity) {
        findById(jobPostingEntity.getJobPostingId());
        return jobPostingRepository.save(jobPostingEntity);
    }

    @Override
    public JobPostingEntity delete(Long jobPostingId) {
        JobPostingEntity deletedJobPosting = findById(jobPostingId);
        jobPostingRepository.deleteById(jobPostingId);
        return deletedJobPosting;
    }


}
