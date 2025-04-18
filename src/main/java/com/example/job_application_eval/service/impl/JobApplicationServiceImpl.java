package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.entities.JobPostingEntity;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.enums.ApplicationStatus;
import com.example.job_application_eval.repository.JobApplicationRepository;
import com.example.job_application_eval.service.EmailService;
import com.example.job_application_eval.service.JobApplicationService;
import com.example.job_application_eval.service.JobPostingService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobPostingService jobPostingService;
    private final Utils utils;
    private final EmailService emailService;

    @Override
    public Page<JobApplicationEntity> getJobApplicationsByUserId(Pageable pageable) {
        Long userId = utils.getCurrentUserId();
        return jobApplicationRepository.findByUser_UserId(userId, pageable);
    }

    @Override
    public JobApplicationEntity apply(Long jobPostingId) {

        JobPostingEntity jobPostingEntity  = jobPostingService.findById(jobPostingId);
        UserEntity userEntity = utils.getCurrentUser();

        JobApplicationEntity jobApplicationEntity = new JobApplicationEntity();
        jobApplicationEntity.setJobPosting(jobPostingEntity);
        jobApplicationEntity.setUser(userEntity);
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
    public Page<JobApplicationEntity> getJobApplicationsByStatus(ApplicationStatus status, Pageable pageable) {
        return jobApplicationRepository.findByStatus(status, pageable);
    }

    @Override
    public Page<JobApplicationEntity> getJobApplicationsByJobPostingId(Long jobPostingId, Pageable pageable) {

        jobPostingService.findById(jobPostingId);
        return jobApplicationRepository.findByJobPosting_JobPostingId(jobPostingId, pageable);
    }

    @Override
    public Page<JobApplicationEntity> getByStatusAndJobPostingId(ApplicationStatus status, Long jobPostingId, Pageable pageable) {
        jobPostingService.findById(jobPostingId);
        return jobApplicationRepository.findByStatusAndJobPosting_JobPostingId(status, jobPostingId, pageable);
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
                    + "<p style=\"font-size: 14px; color: #777;\">Sincerely,<br>The X-Company Team</p>"
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
                    + "<p style=\"font-size: 14px; color: #777;\">Wishing you all the best,<br>The X-Company Team</p>"
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
