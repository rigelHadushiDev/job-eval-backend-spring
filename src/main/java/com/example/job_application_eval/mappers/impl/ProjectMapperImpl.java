package com.example.job_application_eval.mappers.impl;

import com.example.job_application_eval.dtos.ProjectDto;
import com.example.job_application_eval.entities.ProjectEntity;
import com.example.job_application_eval.entities.SkillEntity;
import com.example.job_application_eval.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapperImpl implements Mapper<ProjectEntity, ProjectDto>{
        private ModelMapper modelMapper;

    public ProjectMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

        @Override
        public ProjectDto mapTo(ProjectEntity projectEntity) {
        return modelMapper.map(projectEntity, ProjectDto.class);
    }

        @Override
        public ProjectEntity mapFrom(ProjectDto projectDto) {
        return modelMapper.map(projectDto, ProjectEntity.class);
    }
    }
