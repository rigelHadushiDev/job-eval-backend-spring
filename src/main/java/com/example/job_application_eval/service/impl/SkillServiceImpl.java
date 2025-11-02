package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.dtos.SkillDto;
import com.example.job_application_eval.entities.SkillEntity;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.repository.SkillRepository;
import com.example.job_application_eval.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository repository;
    private final Utils utils;
    private final Mapper<SkillEntity, SkillDto> mapper;

    @Override
    public SkillDto deleteSkill(Long skillId) {

        SkillDto currentSkill = findSkillById(skillId);
        SkillEntity skillEntity = mapper.mapFrom(currentSkill);
        utils.assertCurrentUserOwns(skillEntity.getUser().getUserId());
        repository.deleteById(skillId);
        return currentSkill;
    }

    @Override
    public List<SkillDto> findSkillsByUserId(Long userId) {
        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(userId) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewSkills");
        }
        List<SkillEntity> entities = repository.findByUser_UserId(userId);
        if (entities == null || entities.isEmpty()) return Collections.emptyList();

        return entities.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public SkillDto editSkillEntity(SkillDto SkillDto) {
        SkillEntity skillEntity = mapper.mapFrom(SkillDto);
        SkillEntity currentSkill =  repository.findBySkillId(skillEntity.getSkillId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "skillNotFound"));

        utils.assertCurrentUserOwns(currentSkill.getUser().getUserId());
        SkillEntity editedSkill =  repository.save(skillEntity);

        return mapper.mapTo(editedSkill);
    }

    @Override
    public SkillDto save(SkillDto SkillDto) {
        SkillEntity skillEntity = mapper.mapFrom(SkillDto);
        UserEntity currentUser = utils.getCurrentUser();
        skillEntity.setUser(currentUser);
        SkillEntity savedSkill =  repository.save(skillEntity);
        return mapper.mapTo(savedSkill);
    }

    @Override
    public SkillDto findSkillById(Long skillId) {

        SkillEntity SkillEntity =  repository.findBySkillId(skillId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "skillNotFound"));

        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(SkillEntity.getUser().getUserId()) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewSkill");
        }
        return mapper.mapTo(SkillEntity);
    }
}
