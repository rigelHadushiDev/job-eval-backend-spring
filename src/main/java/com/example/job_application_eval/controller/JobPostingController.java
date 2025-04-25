package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.EducationDto;
import com.example.job_application_eval.dtos.JobPostingDto;
import com.example.job_application_eval.dtos.ProjectDto;
import com.example.job_application_eval.dtos.UserDto;
import com.example.job_application_eval.entities.EducationEntity;
import com.example.job_application_eval.entities.JobPostingEntity;
import com.example.job_application_eval.entities.ProjectEntity;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.service.JobPostingService;
import com.example.job_application_eval.validation.OnJobPostingEdit;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("jobPosting")
@RequiredArgsConstructor
public class JobPostingController {

    private final Mapper<JobPostingEntity, JobPostingDto> mapper;
    private final JobPostingService jobPostingService;

    @GetMapping("/all")
    public Page<JobPostingDto> getJobPostings(
            @RequestParam(value = "closed", required = false) Boolean closed,
            Pageable pageable) {

        Page<JobPostingEntity> jobPostings = jobPostingService.findAllJobPostings(closed, pageable);
        return jobPostings.map(mapper::mapTo);
    }

    @GetMapping("/getJobPosting")
    public ResponseEntity<JobPostingDto> getJobPostingById(@RequestParam Long jobPostingId){
        JobPostingEntity extractedJobPosting = jobPostingService.findById(jobPostingId);
        return new ResponseEntity<>( mapper.mapTo(extractedJobPosting), HttpStatus.OK);
    }

    @GetMapping("/searchByJobTitle")
    public Page<JobPostingDto> getJobPostings(
            @RequestParam(value = "jobTitle") String jobTitle,
            Pageable pageable) {

        Page<JobPostingEntity> jobPostings = jobPostingService.searchByJobTitle(jobTitle, pageable);
        return jobPostings.map(mapper::mapTo);
    }

    @PostMapping("create")
    public ResponseEntity<JobPostingDto> createEducation(@Valid @RequestBody JobPostingDto jobPostingDto){
        JobPostingEntity jobPostingEntity = mapper.mapFrom(jobPostingDto);
        JobPostingEntity savedJobPosting = jobPostingService.save(jobPostingEntity);
        return new ResponseEntity<>( mapper.mapTo(savedJobPosting), HttpStatus.OK);
    }

    @PutMapping("edit")
    public ResponseEntity<JobPostingDto> editJobPosting(@Validated(OnJobPostingEdit.class) @RequestBody JobPostingDto jobPostingDto){
        JobPostingEntity jobPostingEntity = mapper.mapFrom(jobPostingDto);
        JobPostingEntity updatedJobPosting = jobPostingService.edit(jobPostingEntity);
        return new ResponseEntity<>( mapper.mapTo(updatedJobPosting), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<JobPostingDto> deleteJobPosting(@RequestParam Long jobPostingId) {
        JobPostingEntity deletedJobPosting = jobPostingService.delete(jobPostingId);
        return new ResponseEntity<>(mapper.mapTo(deletedJobPosting), HttpStatus.OK);
    }



}
