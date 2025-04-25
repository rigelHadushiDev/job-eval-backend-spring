package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.ApplicantEnglishLevelEntity;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.repository.ApplicantEnglishLevelRepository;
import com.example.job_application_eval.service.ApplicantEnglishLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantEnglishLevelServiceImpl implements ApplicantEnglishLevelService {

    private final ApplicantEnglishLevelRepository repository;
    private final Utils utils;

    @Override
    public ApplicantEnglishLevelEntity deleteApplicantEnglishLevel(Long applicantEnglishLevelId) {
        ApplicantEnglishLevelEntity currentUserLanguage = findApplicantEnglishLevelById(applicantEnglishLevelId);
        utils.assertCurrentUserOwns(currentUserLanguage.getUser().getUserId());
        repository.deleteById(applicantEnglishLevelId);
        return currentUserLanguage;
    }

    @Override
    public ApplicantEnglishLevelEntity findApplicantEnglishLevelByUserId(Long userId) {
        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(userId) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view these applicant english level");
        }
        return repository.findByUser_UserId(userId);
    }

    @Override
    public ApplicantEnglishLevelEntity editApplicantEnglishLevel(ApplicantEnglishLevelEntity applicantEnglishLevelEntity) {
        ApplicantEnglishLevelEntity currentUserLanguage = repository.findByApplicantEnglishLevelId(applicantEnglishLevelEntity.getApplicantEnglishLevelId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant English Level not found"));

        utils.assertCurrentUserOwns(currentUserLanguage.getUser().getUserId());
        applicantEnglishLevelEntity.setUser(currentUserLanguage.getUser());
        return repository.save(applicantEnglishLevelEntity);
    }

    @Override
    public ApplicantEnglishLevelEntity save(ApplicantEnglishLevelEntity applicantEnglishLevelEntity) {
        UserEntity currentUser = utils.getCurrentUser();
        applicantEnglishLevelEntity.setUser(currentUser);
        return repository.save(applicantEnglishLevelEntity);
    }

    @Override
    public ApplicantEnglishLevelEntity findApplicantEnglishLevelById(Long applicantEnglishLevelId) {
        ApplicantEnglishLevelEntity applicantEnglishLevelEntity =  repository.findByApplicantEnglishLevelId(applicantEnglishLevelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant English Level Not Found"));

        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(applicantEnglishLevelEntity.getUser().getUserId()) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this applicant english level");
        }
        return applicantEnglishLevelEntity;
    }
}
