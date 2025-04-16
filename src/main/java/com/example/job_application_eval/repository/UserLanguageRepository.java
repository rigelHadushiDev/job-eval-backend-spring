package com.example.job_application_eval.repository;

import com.example.job_application_eval.entities.UserLanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLanguageRepository extends JpaRepository<UserLanguageEntity, Long> {

    List<UserLanguageEntity> findByUser_UserId(Long userId);

    Optional<UserLanguageEntity> findByLanguageId(Long languageId);

    UserLanguageEntity save(UserLanguageEntity skillEntity);

}