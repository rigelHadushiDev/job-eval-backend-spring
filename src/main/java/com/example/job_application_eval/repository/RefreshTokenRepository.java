package com.example.job_application_eval.repository;

import org.springframework.stereotype.Repository;
import com.example.job_application_eval.entities.RefreshTokenEntity;
import com.example.job_application_eval.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

        Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);
        List<RefreshTokenEntity> findByUserAndRevokedAtIsNull(UserEntity user);
}
