package com.example.job_application_eval.service.impl;
import org.springframework.transaction.annotation.Transactional;
import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.dtos.ApplicantDataRequestDto;
import com.example.job_application_eval.dtos.ApplicantScoreResponseDto;
import com.example.job_application_eval.entities.*;
import com.example.job_application_eval.entities.enums.ApplicationStatus;
import jakarta.mail.MessagingException;
import com.example.job_application_eval.repository.JobApplicationRepository;
import com.example.job_application_eval.repository.specifications.JobApplicationSpecifications;
import com.example.job_application_eval.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobPostingService jobPostingService;
    private final Utils utils;
    private final EmailService emailService;
    private final EducationService educationService;
    private final SkillService skillService;
    private final ApplicantEnglishLevelService applicantEnglishLevelService;
    private final FastApiRequestService fastApiRequestService;



    @Override
    @Transactional
    public JobApplicationEntity apply(Long jobPostingId) {
        JobPostingEntity jobPostingEntity = jobPostingService.findById(jobPostingId);
        UserEntity userEntity = utils.getCurrentUser();

        JobApplicationEntity previousApplication = jobApplicationRepository.findByUser_UserIdAndJobPosting_JobPostingId(
                userEntity.getUserId(), jobPostingId
        );

        if(previousApplication != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alreadyAppliedForThisJob");
        }

        JobApplicationEntity jobApplicationEntity = new JobApplicationEntity();
        jobApplicationEntity.setJobPosting(jobPostingEntity);
        jobApplicationEntity.setUser(userEntity);

        ApplicantDataRequestDto applicantDataRequestDto = new ApplicantDataRequestDto();
        applicantDataRequestDto.setUserId(userEntity.getUserId());
        applicantDataRequestDto.setUsername(userEntity.getUsername());

        List<EducationEntity> eduEntities = educationService.findEducationsByUserId(userEntity.getUserId());
        List<ApplicantDataRequestDto.EducationLevelEntry> educationLevels = Optional.ofNullable(eduEntities)
                .filter(eduList -> !eduList.isEmpty())
                .map(eduList -> eduList.stream()
                        .map(edu -> new ApplicantDataRequestDto.EducationLevelEntry(edu.getEducationLevel()))
                        .toList())
                .orElseGet(() -> List.of(new ApplicantDataRequestDto.EducationLevelEntry(null)));
        applicantDataRequestDto.setEducationLevel(educationLevels);

        Optional<ApplicantEnglishLevelEntity> englishLevelOptional = Optional.ofNullable(applicantEnglishLevelService.findApplicantEnglishLevelByUserId(userEntity.getUserId()));
        applicantDataRequestDto.setEnglishLevel(englishLevelOptional
                .map(ApplicantEnglishLevelEntity::getProficiencyLevel)
                .orElse(null));

        // Skills Data Handling with Optional or Default value
        List<SkillEntity> skillEntities = skillService.findSkillsByUserId(userEntity.getUserId());
        List<ApplicantDataRequestDto.SkillEntry> skills = Optional.ofNullable(skillEntities)
                .filter(skillList -> !skillList.isEmpty())
                .map(skillList -> skillList.stream()
                        .map(skill -> new ApplicantDataRequestDto.SkillEntry(skill.getSkillName(), skill.getSkillProficiency()))
                        .toList())
                .orElseGet(() -> List.of(new ApplicantDataRequestDto.SkillEntry("No skills", 0)));  // Default skill if empty
        applicantDataRequestDto.setSkills(skills);

        applicantDataRequestDto.setJobPostingId(jobPostingId);

        String fastApiUrl = "http://localhost:8000/job-application/";

        try {
            ResponseEntity<ApplicantScoreResponseDto> response = fastApiRequestService.sendRequest(
                    fastApiUrl, HttpMethod.POST, applicantDataRequestDto, ApplicantScoreResponseDto.class
            );

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "applyApiFailed");
            }

            ApplicantScoreResponseDto score = response.getBody();
            jobApplicationEntity.setGeneralScore(score.getFinalScore());
            jobApplicationEntity.setEducationScore(score.getEducationScore());
            jobApplicationEntity.setEnglishScore(score.getEnglishScore());
            jobApplicationEntity.setSkillsScore(score.getSkillScore());
            jobApplicationEntity.setExperienceYearsScore(score.getExperienceYearsScore());
            jobApplicationEntity.setExperienceSimilarityScore(score.getExperienceSimilarityScore());

        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "applyApiFailed");
        }
        return jobApplicationRepository.save(jobApplicationEntity);
    }

    @Override
    public JobApplicationEntity changeStatus(Long jobApplicationId, ApplicationStatus status) {
        JobApplicationEntity jobApplicationEntity =  getByJobApplicationId(jobApplicationId);
        JobPostingEntity jobPostingEntity = jobApplicationEntity.getJobPosting();
        UserEntity user = jobApplicationEntity.getUser();
        jobApplicationEntity.setStatus(status);
        sendStatusUpdateEmail(user,  jobPostingEntity.getJobTitle(), status);
        return jobApplicationRepository.save(jobApplicationEntity);
    }

    @Override
    public Page<JobApplicationEntity> filterMyJobApplications(ApplicationStatus status, Long jobPostingId,
                                                         LocalDateTime applicationDate, String sortBy, String orderType, Pageable pageable) {

        Long userId = utils.getCurrentUserId();

        Specification<JobApplicationEntity> spec = JobApplicationSpecifications.buildSpecification(userId,status,
                jobPostingId, applicationDate, sortBy, orderType);

        return jobApplicationRepository.findAll(spec, pageable);
    }

    @Override
    public Page<JobApplicationEntity> filterAnyJobApplications(Long userId, ApplicationStatus status, Long jobPostingId,
                                                             LocalDateTime applicationDate, String sortBy, String orderType,Pageable pageable) {

        Specification<JobApplicationEntity> spec = JobApplicationSpecifications.buildSpecification(userId, status,
                jobPostingId, applicationDate, sortBy, orderType);

        return jobApplicationRepository.findAll(spec, pageable);
    }

    @Override
    public JobApplicationEntity getByJobApplicationId(Long jobApplicationId){
        return jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Application not found"));
    }


    public void sendStatusUpdateEmail(UserEntity user, String jobTitle, ApplicationStatus status) {
        String fullName = user.getFirstname() + " " + user.getLastname();
        String subject;
        String htmlMessage;

        if (status == ApplicationStatus.ACCEPTED) {
            subject = "🎉 You've Been Accepted for the " + jobTitle + " Position!";
            htmlMessage = "<html>"
                    + "<body style=\"font-family: Arial, sans-serif;\">"
                    + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                    + "<h2 style=\"color: #28a745;\">Congratulations, " + fullName + "!</h2>"
                    + "<p style=\"font-size: 16px;\">We are pleased to inform you that you have been <strong>accepted</strong> for the <strong>" + jobTitle + "</strong> role.</p>"
                    + "<p style=\"font-size: 16px;\">We'll follow up soon with next steps, onboarding, and start date information.</p>"
                    + "<p style=\"font-size: 14px; color: #777; margin-top: 20px;\">For any questions, feel free to reach out to us.</p>"
                    + "<p style=\"font-size: 14px; color: #777;\">Sincerely,<br>The CodePioneers Team</p>"
                    + "</div></body></html>";
        } else if (status == ApplicationStatus.REJECTED) {
            subject = "Your Application for " + jobTitle + " Has Been Reviewed";
            htmlMessage = "<html>"
                    + "<body style=\"font-family: Arial, sans-serif;\">"
                    + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                    + "<h2 style=\"color: #dc3545;\">Hello " + fullName + ",</h2>"
                    + "<p style=\"font-size: 16px;\">Thank you for applying for the <strong>" + jobTitle + "</strong> position.</p>"
                    + "<p style=\"font-size: 16px;\">After careful consideration, we regret to inform you that you were not selected for this role.</p>"
                    + "<p style=\"font-size: 16px;\">We encourage you to apply again in the future, and we sincerely appreciate your interest in joining our team.</p>"
                    + "<p style=\"font-size: 14px; color: #777;\">Wishing you all the best,<br>The CodePioneers Team</p>"
                    + "</div></body></html>";
        } else {
            return;
        }
        try {
            emailService.sendEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
