package com.example.job_application_eval.repository;

import com.example.job_application_eval.entities.WorkExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperienceEntity, Long> {


    List<WorkExperienceEntity> findByUser_UserId(Long userId);

    Optional<WorkExperienceEntity> findByWorkExperienceId(Long workExperienceId);

    WorkExperienceEntity save(WorkExperienceEntity workExperienceEntity);

}
