package com.example.job_application_eval.config.utils;

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
        LocalDate today = LocalDate.now();
        boolean isFinished = Boolean.TRUE.equals(workExp.getFinished());


        if (startDate.isAfter(today)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date cannot be in the future.");
        }

        if (isFinished) {
            if (endDate == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date is required when experience is marked as finished.");
            }
            if (endDate.isBefore(startDate)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date must be after start date.");
            }
        }

        LocalDate effectiveEndDate = isFinished ? endDate : today;
        Period period = Period.between(startDate, effectiveEndDate);

        double years = period.getYears() + (period.getMonths() / 12.0) + (period.getDays() / 365.0);
        workExp.setTotalYears(years);
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}