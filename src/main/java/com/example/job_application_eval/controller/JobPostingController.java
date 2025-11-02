package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.JobPostingDto;
import com.example.job_application_eval.entities.JobPostingEntity;
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

    private final JobPostingService jobPostingService;

    @GetMapping("/all")
    public Page<JobPostingDto> getJobPostings(@RequestParam(value = "closed", required = false) Boolean closed, Pageable pageable) {
        return jobPostingService.findAllJobPostings(closed, pageable);
    }

    @GetMapping("/getJobPosting")
    public ResponseEntity<JobPostingDto> getJobPostingById(@RequestParam Long jobPostingId){
        JobPostingDto extractedJobPosting = jobPostingService.findById(jobPostingId);
        return ResponseEntity.ok(extractedJobPosting);
    }

    @GetMapping("/searchByJobTitle")
    public Page<JobPostingDto> getJobPostings( @RequestParam(value = "jobTitle") String jobTitle, Pageable pageable) {
        return jobPostingService.searchByJobTitle(jobTitle, pageable);
    }

    @PostMapping("create")
    public ResponseEntity<JobPostingDto> createEducation(@Valid @RequestBody JobPostingDto jobPostingDto){
        JobPostingDto savedJobPosting = jobPostingService.save(jobPostingDto);
        return ResponseEntity.ok(savedJobPosting);
    }

    @PutMapping("edit")
    public ResponseEntity<JobPostingDto> editJobPosting(@Validated(OnJobPostingEdit.class) @RequestBody JobPostingDto jobPostingDto){
        JobPostingDto updatedJobPosting = jobPostingService.edit(jobPostingDto);
        return ResponseEntity.ok(updatedJobPosting);
    }

    @DeleteMapping()
    public ResponseEntity<JobPostingDto> deleteJobPosting(@RequestParam Long jobPostingId) {
        JobPostingDto deletedJobPosting = jobPostingService.delete(jobPostingId);
        return ResponseEntity.ok(deletedJobPosting);
    }



}
