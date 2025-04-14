package com.example.job_application_eval.service;

import com.example.job_application_eval.entities.SkillEntity;

import java.util.List;

public interface SkillService {

    SkillEntity deleteSkill(Long skillId);

    List<SkillEntity> findSkillsByUserId(Long userId);

    SkillEntity editSkillEntity(SkillEntity SkillEntity);

    SkillEntity save(SkillEntity SkillEntity);

    SkillEntity findSkillById(Long skillId);

}
