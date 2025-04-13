package com.example.job_application_eval.repository;

import com.example.job_application_eval.entities.EducationEntity;
import com.example.job_application_eval.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<EducationEntity, Long> {

    List<EducationEntity> findByUser_UserId(Long userId);

    EducationEntity findByEducationId(Long educationId);

}
