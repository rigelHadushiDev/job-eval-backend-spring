package com.example.job_application_eval.repository;

import com.example.job_application_eval.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository  extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findByUser_UserId(Long userId);

    ProjectEntity findByProjectId(Long projectId);

    ProjectEntity save(ProjectEntity skillEntity);

}