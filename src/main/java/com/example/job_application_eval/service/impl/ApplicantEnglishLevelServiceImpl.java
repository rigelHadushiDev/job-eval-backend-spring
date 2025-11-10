package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.dtos.ApplicantEnglishLevelDto;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.ApplicantEnglishLevelEntity;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.repository.ApplicantEnglishLevelRepository;
import com.example.job_application_eval.service.ApplicantEnglishLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class ApplicantEnglishLevelServiceImpl implements ApplicantEnglishLevelService {

    private final ApplicantEnglishLevelRepository repository;
    private final Utils utils;
    private final Mapper<ApplicantEnglishLevelEntity, ApplicantEnglishLevelDto> mapper;

    @Override
    public ApplicantEnglishLevelDto deleteApplicantEnglishLevel(Long applicantEnglishLevelId) {

        ApplicantEnglishLevelEntity currentUserLanguage = findApplicantEnglishLevelById(applicantEnglishLevelId);
        utils.assertCurrentUserOwns(currentUserLanguage.getUser().getUserId());
        repository.deleteById(applicantEnglishLevelId);
        return mapper.mapTo(currentUserLanguage);
    }

    @Override
    public ApplicantEnglishLevelDto findApplicantEnglishLevelByUserId(Long userId) {
        UserEntity currentUser = utils.getCurrentUser();
        if (!currentUser.getUserId().equals(userId) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewApplicantEnglishLevel");
        }

        ApplicantEnglishLevelEntity found = repository.findByUser_UserId(userId);
        return (found == null) ? null : mapper.mapTo(found);
    }

    @Override
    public ApplicantEnglishLevelDto editApplicantEnglishLevel(ApplicantEnglishLevelDto applicantEnglishLevelDto) {

        ApplicantEnglishLevelEntity applicantEnglishLevelEntity = mapper.mapFrom(applicantEnglishLevelDto);
        ApplicantEnglishLevelEntity currentUserLanguage =
                repository.findByApplicantEnglishLevelId(applicantEnglishLevelEntity.getApplicantEnglishLevelId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "applicantEnglishLevelNotFound"));

        utils.assertCurrentUserOwns(currentUserLanguage.getUser().getUserId());
        applicantEnglishLevelEntity.setUser(currentUserLanguage.getUser());
        ApplicantEnglishLevelEntity savedApplicantEnglishLevel =  repository.save(applicantEnglishLevelEntity);
        return mapper.mapTo(savedApplicantEnglishLevel);
    }

    @Override
    public ApplicantEnglishLevelDto save(ApplicantEnglishLevelDto applicantEnglishLevelDto) {

        ApplicantEnglishLevelEntity applicantEnglishLevelEntity = mapper.mapFrom(applicantEnglishLevelDto);
        UserEntity currentUser = utils.getCurrentUser();
        applicantEnglishLevelEntity.setUser(currentUser);
        ApplicantEnglishLevelEntity savedApplicantEnglishLevel = repository.save(applicantEnglishLevelEntity);
        return mapper.mapTo(savedApplicantEnglishLevel);
    }

    @Override
    public ApplicantEnglishLevelEntity findApplicantEnglishLevelById(Long applicantEnglishLevelId) {
        ApplicantEnglishLevelEntity applicantEnglishLevelEntity =
                repository.findByApplicantEnglishLevelId(applicantEnglishLevelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "applicantEnglishLevelNotFound"));

        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(applicantEnglishLevelEntity.getUser().getUserId()) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewApplicantEnglishLevel");
        }
        return applicantEnglishLevelEntity;
    }
}
