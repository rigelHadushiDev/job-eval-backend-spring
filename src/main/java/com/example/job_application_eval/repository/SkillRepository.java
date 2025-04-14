package com.example.job_application_eval.repository;

import com.example.job_application_eval.entities.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository  extends JpaRepository<SkillEntity, Long> {

    List<SkillEntity> findByUser_UserId(Long userId);

    Optional<SkillEntity> findBySkillId(Long skillId);

    SkillEntity save(SkillEntity skillEntity);

}