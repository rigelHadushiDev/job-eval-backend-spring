package com.example.job_application_eval.service.impl;
import org.springframework.transaction.annotation.Transactional;
import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.dtos.WorkExperienceFastAPIDto;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.WorkExperienceEntity;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.repository.WorkExperienceRepository;
import com.example.job_application_eval.service.FastApiRequestService;
import com.example.job_application_eval.service.WorkExperienceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.databind.JsonNode;
@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceRepository repository;
    private final Utils utils;
    private final FastApiRequestService fastApiRequestService;

    @Override
    @Transactional
    public WorkExperienceEntity deleteWorkExperience(Long workExperienceId) {
        WorkExperienceEntity currentExperience = findWorkExperienceById(workExperienceId);
        utils.assertCurrentUserOwns(currentExperience.getUser().getUserId());
        repository.deleteById(workExperienceId);

        String fastApiUrl = "http://localhost:8000/deleteWorkExperience/" + workExperienceId;

        try {
            ResponseEntity<String> response = fastApiRequestService.sendRequest(
                    fastApiUrl, HttpMethod.DELETE, null, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "deleteWorkExperienceFailed");
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "deleteWorkExperienceFailed");
        }
        return currentExperience;
    }

    @Override
    public List<WorkExperienceEntity> findWorkExperiencesByUserId(Long userId) {
        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(userId) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewWorkExperiences");
        }
        return repository.findByUser_UserId(userId);
    }

    @Override
    @Transactional
    public WorkExperienceEntity editWorkExperience(WorkExperienceEntity workExperienceEntity) {
        WorkExperienceEntity currentExperience = repository.findByWorkExperienceId(workExperienceEntity.getWorkExperienceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "workExperienceNotFound"));

        utils.assertCurrentUserOwns(currentExperience.getUser().getUserId());
        utils.validateAndUpdateWorkExperience(workExperienceEntity);
        workExperienceEntity.setUser(currentExperience.getUser());

        WorkExperienceEntity edited = repository.save(workExperienceEntity);
        String fastApiUrl = "http://localhost:8000/editWorkExperience/";
        WorkExperienceFastAPIDto workExperienceDTO = getWorkExperienceFastAPIDto(edited);
        try {
            ResponseEntity<String> response = fastApiRequestService.sendRequest(
                    fastApiUrl, HttpMethod.PUT, workExperienceDTO, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "editWorkExperienceFailed");
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "editWorkExperienceFailed");
        }
        return edited;
    }

    @Override
    @Transactional
    public WorkExperienceEntity save(WorkExperienceEntity workExperienceEntity) {
        UserEntity currentUser = utils.getCurrentUser();
        workExperienceEntity.setUser(currentUser);
        utils.validateAndUpdateWorkExperience(workExperienceEntity);

        WorkExperienceEntity saved = repository.save(workExperienceEntity);
        String fastApiUrl = "http://localhost:8000/work-experience/";
        WorkExperienceFastAPIDto workExperienceDTO = getWorkExperienceFastAPIDto(saved);

        try {
            ResponseEntity<String> response = fastApiRequestService.sendRequest(
                    fastApiUrl, HttpMethod.POST, workExperienceDTO, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "saveWorkExperienceFailed");
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "saveWorkExperienceFailed");
        }
        return saved;
    }

    private static WorkExperienceFastAPIDto getWorkExperienceFastAPIDto(WorkExperienceEntity workExperienceEntity) {
        WorkExperienceFastAPIDto workExperienceDTO = new WorkExperienceFastAPIDto();
        workExperienceDTO.setWorkExperienceId(String.valueOf(workExperienceEntity.getWorkExperienceId()));
        workExperienceDTO.setUserId(Math.toIntExact(workExperienceEntity.getUser().getUserId()));
        workExperienceDTO.setWorkExperienceTitle(workExperienceEntity.getJobTitle());
        workExperienceDTO.setWorkExperienceDescription(workExperienceEntity.getDescription());
        workExperienceDTO.setTotalYears(workExperienceEntity.getTotalYears());
        return workExperienceDTO;
    }

    @Override
    public WorkExperienceEntity findWorkExperienceById(Long workExperienceId) {
        WorkExperienceEntity workExpEntity =  repository.findByWorkExperienceId(workExperienceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "workExperienceNotFound"));

        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(workExpEntity.getUser().getUserId()) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewWorkExperience");
        }
        return workExpEntity;
    }

}
