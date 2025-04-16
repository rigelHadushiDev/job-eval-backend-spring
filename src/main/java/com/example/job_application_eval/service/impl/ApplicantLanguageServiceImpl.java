package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.ApplicantLanguageEntity;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.repository.ApplicantLanguageRepository;
import com.example.job_application_eval.service.ApplicantLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantLanguageServiceImpl implements ApplicantLanguageService {

    private final ApplicantLanguageRepository repository;
    private final Utils utils;

    @Override
    public ApplicantLanguageEntity deleteApplicantLanguage(Long userLanguageId) {
        ApplicantLanguageEntity currentUserLanguage = findApplicantLanguageById(userLanguageId);
        utils.assertCurrentUserOwns(currentUserLanguage.getUser().getUserId());
        repository.deleteById(userLanguageId);
        return currentUserLanguage;
    }

    @Override
    public List<ApplicantLanguageEntity> findApplicantLanguagesByUserId(Long userId) {
        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(userId) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view these applicant languages");
        }
        return repository.findByUser_UserId(userId);
    }

    @Override
    public ApplicantLanguageEntity editApplicantLanguage(ApplicantLanguageEntity applicantLanguageEntity) {
        ApplicantLanguageEntity currentUserLanguage = repository.findByApplicantLanguageId(applicantLanguageEntity.getApplicantLanguageId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Applicant Language not found"));

        utils.assertCurrentUserOwns(currentUserLanguage.getUser().getUserId());
        applicantLanguageEntity.setUser(currentUserLanguage.getUser());
        return repository.save(applicantLanguageEntity);
    }

    @Override
    public ApplicantLanguageEntity save(ApplicantLanguageEntity applicantLanguageEntity) {
        UserEntity currentUser = utils.getCurrentUser();
        applicantLanguageEntity.setUser(currentUser);
        return repository.save(applicantLanguageEntity);
    }

    @Override
    public ApplicantLanguageEntity findApplicantLanguageById(Long userLanguageId) {
        ApplicantLanguageEntity applicantLanguageEntity =  repository.findByApplicantLanguageId(userLanguageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USer Language Not Found"));

        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(applicantLanguageEntity.getUser().getUserId()) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this user language");
        }
        return applicantLanguageEntity;
    }
}
