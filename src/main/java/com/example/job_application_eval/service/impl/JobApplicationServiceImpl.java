package com.example.job_application_eval.service.impl;
import com.example.job_application_eval.dtos.*;
import com.example.job_application_eval.entities.enums.ProficiencyLevel;
import com.example.job_application_eval.mappers.Mapper;
import org.springframework.transaction.annotation.Transactional;
import com.example.job_application_eval.config.utils.Utils;
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
    private final Mapper<JobApplicationEntity, JobApplicationHighRoleDto> highRolemapper;
    private final Mapper<JobApplicationEntity, JobApplicationDto> jobApplicationMapper;
    private final Mapper<JobPostingEntity, JobPostingDto> jobPostingMapper;
    private final Mapper<EducationEntity, EducationDto> educationMapper;
    private final Mapper<ApplicantEnglishLevelEntity, ApplicantEnglishLevelDto> applicationEnglishMapper;
    private final Mapper<SkillEntity, SkillDto> skillsMapper;


    @Override
    @Transactional
    public JobApplicationDto apply(Long jobPostingId) {

        JobPostingDto jobPostingDto = jobPostingService.findById(jobPostingId);
        JobPostingEntity jobPostingEntity = jobPostingMapper.mapFrom(jobPostingDto);

        if (Boolean.TRUE.equals(jobPostingEntity.getClosed())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "jobPostingClosed");
        }

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

        List<EducationDto> eduDtos = educationService.findEducationsByUserId(userEntity.getUserId());
        List<EducationEntity> eduEntities = eduDtos.stream()
                .map(educationMapper::mapFrom)
                .toList();

        List<ApplicantDataRequestDto.EducationLevelEntry> educationLevels = Optional.ofNullable(eduEntities)
                .filter(eduList -> !eduList.isEmpty())
                .map(eduList -> eduList.stream()
                        .map(edu -> new ApplicantDataRequestDto.EducationLevelEntry(edu.getEducationLevel()))
                        .toList())
                .orElseGet(() -> List.of(new ApplicantDataRequestDto.EducationLevelEntry(null)));
        applicantDataRequestDto.setEducationLevel(educationLevels);

        String level = String.valueOf(Optional
                .ofNullable(applicantEnglishLevelService.findApplicantEnglishLevelByUserId(userEntity.getUserId()))
                .map(applicationEnglishMapper::mapFrom)
                .map(ApplicantEnglishLevelEntity::getProficiencyLevel)
                .orElse(null));

        applicantDataRequestDto.setEnglishLevel(ProficiencyLevel.valueOf(level));

        List<SkillDto> skillDto= skillService.findSkillsByUserId(userEntity.getUserId());
        List<SkillEntity> skillEntities = skillDto.stream()
                .map(skillsMapper::mapFrom)
                .toList();
        List<ApplicantDataRequestDto.SkillEntry> skills = Optional.of(skillEntities)
                .filter(skillList -> !skillList.isEmpty())
                .map(skillList -> skillList.stream()
                        .map(skill -> new ApplicantDataRequestDto.SkillEntry(skill.getSkillName(), skill.getSkillProficiency()))
                        .toList())
                .orElseGet(() -> List.of(new ApplicantDataRequestDto.SkillEntry("No skills", 0)));

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
        JobApplicationEntity jobApplication = jobApplicationRepository.save(jobApplicationEntity);
        return jobApplicationMapper.mapTo(jobApplication);
    }

    @Override
    public JobApplicationDto changeStatus(Long jobApplicationId, ApplicationStatus status) {

        JobApplicationDto jobApplicationDto =  getByJobApplicationId(jobApplicationId);
        JobApplicationEntity jobApplicationEntity = jobApplicationMapper.mapFrom(jobApplicationDto);

        JobPostingEntity jobPostingEntity = jobApplicationEntity.getJobPosting();
        UserEntity user = jobApplicationEntity.getUser();
        jobApplicationEntity.setStatus(status);
        sendStatusUpdateEmail(user,  jobPostingEntity.getJobTitle(), status);

         JobApplicationEntity statusChangedApplication =  jobApplicationRepository.save(jobApplicationEntity);
        return jobApplicationMapper.mapTo(statusChangedApplication);
    }

    @Override
    public Page<JobApplicationDto> filterMyJobApplications(ApplicationStatus status, Long jobPostingId,
                                                         LocalDateTime applicationDate, String sortBy, String orderType, String fullName,String jobTitle, Boolean closed,
                                                              Long jobApplicationId, Pageable pageable) {

        Long userId = utils.getCurrentUserId();

        Specification<JobApplicationEntity> spec = JobApplicationSpecifications.buildSpecification(userId,status,jobPostingId, applicationDate, sortBy,
                orderType,fullName, jobTitle, closed, jobApplicationId);

        Page<JobApplicationEntity> jobApplicationEntities =  jobApplicationRepository.findAll(spec, pageable);
        return jobApplicationEntities.map(jobApplicationMapper::mapTo);
    }

    @Override
    public Page<JobApplicationHighRoleDto> filterAnyJobApplications(Long userId, ApplicationStatus status, Long jobPostingId,
                                                             LocalDateTime applicationDate, String sortBy, String orderType,String fullName,String jobTitle, Boolean closed,
                                                                 Long jobApplicationId ,Pageable pageable) {

        Specification<JobApplicationEntity> spec = JobApplicationSpecifications.buildSpecification(userId, status,
                jobPostingId, applicationDate, sortBy, orderType,fullName, jobTitle, closed, jobApplicationId);

        Page<JobApplicationEntity> jobApplications = jobApplicationRepository.findAll(spec, pageable);
        return jobApplications.map(highRolemapper::mapTo);
    }

    @Override
    public JobApplicationDto getByJobApplicationId(Long jobApplicationId){
        JobApplicationEntity jobApplicationEntity = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Application not found"));
        return jobApplicationMapper.mapTo(jobApplicationEntity);
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
