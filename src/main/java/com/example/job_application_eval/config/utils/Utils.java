package com.example.job_application_eval.config.utils;

import com.example.job_application_eval.entities.ProjectEntity;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.WorkExperienceEntity;
import com.example.job_application_eval.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class Utils {

    private final UserRepository userRepository;

    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "userNotFound"));
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    public void assertCurrentUserOwns(Long entityOwnerId) {
        Long currentUserId = getCurrentUserId();
        if (!currentUserId.equals(entityOwnerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "notOwnerOfResource");
        }
    }

    public void validateAndUpdateWorkExperience(WorkExperienceEntity workExp) {
        LocalDate startDate = toLocalDate(workExp.getStartDate());
        LocalDate endDate = workExp.getEndDate() != null ? toLocalDate(workExp.getEndDate()) : null;
        boolean isFinished = workExp.getFinished();

        LocalDate effectiveEndDate = validateDates(startDate, endDate, isFinished, "experience");

        // Calculate total years of experience
        long totalDays = ChronoUnit.DAYS.between(startDate, effectiveEndDate);
        double years = totalDays / 365.25;
        double roundedYears = Math.round(years * 10.0) / 10.0;

        workExp.setTotalYears(roundedYears);
    }

    public void validateAndUpdateProject(ProjectEntity project) {
        LocalDate startDate = toLocalDate(project.getStartDate());
        LocalDate endDate = project.getEndDate() != null ? toLocalDate(project.getEndDate()) : null;
        boolean isFinished = project.getFinished();

        validateDates(startDate, endDate, isFinished, "project");
    }

    private LocalDate validateDates(LocalDate startDate, LocalDate endDate, boolean isFinished, String context) {
        LocalDate today = LocalDate.now();

        if (startDate.isAfter(today)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "startDateCannotBeInFuture");
        }

        if (isFinished) {
            if (endDate == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endDateRequiredWhen" + capitalize(context) + "IsFinished");
            }
            if (endDate.isBefore(startDate)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endDateMustBeAfterStartDate");
            }
            return endDate;
        }

        return today;
    }

    private String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}