package com.example.job_application_eval.repository;

import com.example.job_application_eval.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findUserByUserId(Long userId);
    Optional<UserEntity> findByUsername(String username);
    Page<UserEntity> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);


}
