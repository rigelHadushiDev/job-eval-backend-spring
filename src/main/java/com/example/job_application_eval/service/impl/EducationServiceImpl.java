package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.entities.EducationEntity;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.enums.EducationLevel;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.repository.EducationRepository;
import com.example.job_application_eval.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final Utils utils;

    @Override
    public EducationEntity deleteEducation(Long educationId) {
        EducationEntity currentEducation = findEducationById(educationId);
        utils.assertCurrentUserOwns(currentEducation.getUser().getUserId());
        educationRepository.deleteById(educationId);
        return currentEducation;
    }

    @Override
    public List<EducationEntity> findEducationsByUserId(Long userId) {
        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(userId) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewEducations");
        }
        return educationRepository.findByUser_UserId(userId);
    }

    @Override
    public EducationEntity editEducation(EducationEntity educationEntity) {

        EducationEntity currentEducation =  educationRepository.findByEducationId(educationEntity.getEducationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "educationNotFound"));

        utils.assertCurrentUserOwns(currentEducation.getUser().getUserId());
        return educationRepository.save(educationEntity);
    }

    @Override
    public EducationEntity save(EducationEntity educationEntity) {

        UserEntity currentUser = utils.getCurrentUser();
        educationEntity.setUser(currentUser);
        return educationRepository.save(educationEntity);
    }

    @Override
    public EducationEntity findEducationById(Long educationId) {
       EducationEntity educationEntity =  educationRepository.findByEducationId(educationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "educationNotFound"));

        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(educationEntity.getUser().getUserId()) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewEducation");
        }
        return educationEntity;
    }





}
