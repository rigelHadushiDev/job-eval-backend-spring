package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.WorkExperienceEntity;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.repository.WorkExperienceRepository;
import com.example.job_application_eval.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceRepository repository;
    private final Utils utils;

    @Override
    public WorkExperienceEntity deleteWorkExperience(Long workExperienceId) {
        WorkExperienceEntity currentExperience = findWorkExperienceById(workExperienceId);
        utils.assertCurrentUserOwns(currentExperience.getUser().getUserId());
        repository.deleteById(workExperienceId);
        return currentExperience;
    }

    @Override
    public List<WorkExperienceEntity> findWorkExperiencesByUserId(Long userId) {
        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(userId) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view these work experiences");
        }
        return repository.findByUser_UserId(userId);
    }

    @Override
    public WorkExperienceEntity editWorkExperience(WorkExperienceEntity workExperienceEntity) {
        WorkExperienceEntity currentExperience = repository.findByWorkExperienceId(workExperienceEntity.getWorkExperienceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Experience not found"));

        utils.assertCurrentUserOwns(currentExperience.getUser().getUserId());
        utils.validateAndUpdateWorkExperience(workExperienceEntity);
        workExperienceEntity.setUser(currentExperience.getUser());
        return repository.save(workExperienceEntity);
    }

    @Override
    public WorkExperienceEntity save(WorkExperienceEntity workExperienceEntity) {
        UserEntity currentUser = utils.getCurrentUser();
        workExperienceEntity.setUser(currentUser);
        utils.validateAndUpdateWorkExperience(workExperienceEntity);
        return repository.save(workExperienceEntity);
    }

    @Override
    public WorkExperienceEntity findWorkExperienceById(Long workExperienceId) {
        WorkExperienceEntity workExpEntity =  repository.findByWorkExperienceId(workExperienceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "workExperienceNotFound"));

        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(workExpEntity.getUser().getUserId()) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this work experience");
        }
        return workExpEntity;
    }

}
