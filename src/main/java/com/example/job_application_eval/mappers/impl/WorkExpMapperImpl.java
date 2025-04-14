package com.example.job_application_eval.mappers.impl;

import com.example.job_application_eval.dtos.WorkExperienceDto;
import com.example.job_application_eval.entities.WorkExperienceEntity;
import com.example.job_application_eval.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WorkExpMapperImpl implements Mapper<WorkExperienceEntity, WorkExperienceDto> {
    private ModelMapper modelMapper;

    public WorkExpMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public WorkExperienceDto mapTo(WorkExperienceEntity WorkExperienceEntity) {
        return modelMapper.map(WorkExperienceEntity, WorkExperienceDto.class);
    }

    @Override
    public WorkExperienceEntity mapFrom(WorkExperienceDto WorkExperienceDto) {
        return modelMapper.map(WorkExperienceDto, WorkExperienceEntity.class);
    }
}

