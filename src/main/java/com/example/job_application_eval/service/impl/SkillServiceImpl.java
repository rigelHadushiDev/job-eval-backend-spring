package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.config.utils.Utils;
import com.example.job_application_eval.entities.SkillEntity;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.repository.SkillRepository;
import com.example.job_application_eval.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository repository;
    private final Utils utils;

    @Override
    public SkillEntity deleteSkill(Long skillId) {
        SkillEntity currentSkill = findSkillById(skillId);
        utils.assertCurrentUserOwns(currentSkill.getUser().getUserId());
        repository.deleteById(skillId);
        return currentSkill;
    }

    @Override
    public List<SkillEntity> findSkillsByUserId(Long userId) {
        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(userId) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewSkills");
        }
        return repository.findByUser_UserId(userId);
    }

    @Override
    public SkillEntity editSkillEntity(SkillEntity SkillEntity) {
        SkillEntity currentSkill =  repository.findBySkillId(SkillEntity.getSkillId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "skillNotFound"));

        utils.assertCurrentUserOwns(currentSkill.getUser().getUserId());
        return repository.save(SkillEntity);
    }

    @Override
    public SkillEntity save(SkillEntity SkillEntity) {
        UserEntity currentUser = utils.getCurrentUser();
        SkillEntity.setUser(currentUser);
        return repository.save(SkillEntity);
    }

    @Override
    public SkillEntity findSkillById(Long skillId) {
        SkillEntity SkillEntity =  repository.findBySkillId(skillId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "skillNotFound"));

        UserEntity currentUser = utils.getCurrentUser();
        if(!currentUser.getUserId().equals(SkillEntity.getUser().getUserId()) && currentUser.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unAuthorizedToViewSkill");
        }
        return SkillEntity;
    }
}
