package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.dtos.EducationDto;
import com.example.job_application_eval.entities.EducationEntity;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.enums.EducationLevel;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.repository.EducationRepository;
import com.example.job_application_eval.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final Utils utils;
    private final Mapper<EducationEntity, EducationDto> educationMapper;

    @Override
    public EducationDto deleteEducation(Long educationId) {

        EducationDto currentEducation = findEducationById(educationId);
        EducationEntity educationEntity = educationMapper.mapFrom(currentEducation);
        utils.assertCurrentUserOwns(educationEntity.getUser().getUserId());
        educationRepository.deleteById(educationId);
        return educationMapper.mapTo(educationEntity);
    }

    @Override
    public List<EducationDto> findEducationsByUserId(Long userId) {

        UserEntity currentUser = utils.getCurrentUser();

        if(!currentUser.getUserId().equals(userId) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewEducations");
        }

        List<EducationEntity> educationEntities = educationRepository.findByUser_UserId(userId);
        return educationEntities.stream()
                .map(educationMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public EducationDto editEducation(EducationDto educationDto) {

        EducationEntity educationEntity = educationMapper.mapFrom(educationDto);
        EducationEntity currentEducation =  educationRepository.findByEducationId(educationEntity.getEducationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "educationNotFound"));

        utils.assertCurrentUserOwns(currentEducation.getUser().getUserId());
        EducationEntity editedEducation =   educationRepository.save(educationEntity);
        return educationMapper.mapTo(editedEducation);
    }

    @Override
    public EducationDto save(EducationDto educationDto) {

        EducationEntity educationEntity = educationMapper.mapFrom(educationDto);
        UserEntity currentUser = utils.getCurrentUser();
        educationEntity.setUser(currentUser);
        EducationEntity savedEducation =  educationRepository.save(educationEntity);
        return educationMapper.mapTo(savedEducation);
    }

    @Override
    public EducationDto findEducationById(Long educationId) {

       EducationEntity educationEntity =  educationRepository.findByEducationId(educationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "educationNotFound"));

        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(educationEntity.getUser().getUserId()) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewEducation");
        }
        return educationMapper.mapTo(educationEntity);
    }





}
