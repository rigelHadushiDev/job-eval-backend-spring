package com.example.job_application_eval.service.impl;


import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.dtos.JobPostingFastApiDto;
import com.example.job_application_eval.entities.JobPostingEntity;
import com.example.job_application_eval.entities.enums.WorkingType;
import com.example.job_application_eval.repository.JobPostingRepository;
import com.example.job_application_eval.service.FastApiRequestService;
import com.example.job_application_eval.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class JobPostingServiceImpl  implements JobPostingService {

    private final Utils utils;
    private final JobPostingRepository jobPostingRepository;
    private final FastApiRequestService fastApiRequestService;

    @Override
    public JobPostingEntity save(JobPostingEntity jobPostingEntity) {

        verifyLocationForWorkingType(jobPostingEntity);

        JobPostingEntity savedJobPosting = jobPostingRepository.save(jobPostingEntity);
        String fastApiUrl = "http://localhost:8000/job-posting/";
        JobPostingFastApiDto jobPostingDto = getJobPostingFastApiDto(savedJobPosting);

        try {
            ResponseEntity<String> response = fastApiRequestService.sendRequest(
                    fastApiUrl, HttpMethod.POST, jobPostingDto, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "saveJobPostingFailed");
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "saveJobPostingFailed");
        }
        return savedJobPosting;
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

        verifyLocationForWorkingType(jobPostingEntity);
        JobPostingEntity edited = jobPostingRepository.save(jobPostingEntity);
        String fastApiUrl = "http://localhost:8000/editJobPosting/";
        JobPostingFastApiDto jobPostingDTO = getJobPostingFastApiDto(edited);
        try {
            ResponseEntity<String> response = fastApiRequestService.sendRequest(
                    fastApiUrl, HttpMethod.PUT, jobPostingDTO, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "editJobPostingFailed");
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "editJobPostingFailed");
        }
        return edited;
    }

    private void verifyLocationForWorkingType(JobPostingEntity jobPostingEntity) {
        if (jobPostingEntity.getWorkingType() == WorkingType.HYBRID
                || jobPostingEntity.getWorkingType() == WorkingType.ON_SITE) {

            boolean missingCity = !StringUtils.hasText(jobPostingEntity.getCity());
            boolean missingCountry = !StringUtils.hasText(jobPostingEntity.getCountry());

            if (missingCity || missingCountry) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "officesShouldHaveALocation"
                );
            }
        }
    }

    @Override
    public JobPostingEntity delete(Long jobPostingId) {
        JobPostingEntity deletedJobPosting = findById(jobPostingId);
        jobPostingRepository.deleteById(jobPostingId);

        String fastApiUrl = "http://localhost:8000/deleteJobPosting/" + jobPostingId;

        try {
            ResponseEntity<String> response = fastApiRequestService.sendRequest(
                    fastApiUrl, HttpMethod.DELETE, null, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "deleteJobPostingFailed");
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "deleteJobPostingFailed");
        }
        return deletedJobPosting;
    }

    private static JobPostingFastApiDto getJobPostingFastApiDto(JobPostingEntity entity) {
        JobPostingFastApiDto dto = new JobPostingFastApiDto();
        dto.setJobPostingId(String.valueOf(entity.getJobPostingId().intValue()));
        dto.setJobPostingTitle(entity.getJobTitle());
        dto.setJobPostingDesc(entity.getJobDescription());
        dto.setRequiredEnglishLevel(entity.getRequiredEnglishLevel().name());
        dto.setRequiredExperienceYears(entity.getRequiredExperienceYears().floatValue());
        dto.setRequiredSkills(entity.getRequiredSkills());
        return dto;
    }
}
