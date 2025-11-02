package com.example.job_application_eval.service;

import com.example.job_application_eval.dtos.SkillDto;
import com.example.job_application_eval.entities.SkillEntity;

import java.util.List;

public interface SkillService {

    SkillDto deleteSkill(Long skillId);

    List<SkillDto> findSkillsByUserId(Long userId);

    SkillDto editSkillEntity(SkillDto skillDto);

    SkillDto save(SkillDto skillDto);

    SkillDto findSkillById(Long skillId);

}
