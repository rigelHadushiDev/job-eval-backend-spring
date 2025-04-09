package com.example.job_application_eval.mappers.impl;

import com.example.job_application_eval.dtos.WorkExpDto;
import com.example.job_application_eval.entities.WorkExpEntity;
import com.example.job_application_eval.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WorkExpMapperImpl implements Mapper<WorkExpEntity, WorkExpDto> {
    private ModelMapper modelMapper;

    public WorkExpMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public WorkExpDto mapTo(WorkExpEntity WorkExpEntity) {
        return modelMapper.map(WorkExpEntity, WorkExpDto.class);
    }

    @Override
    public WorkExpEntity mapFrom(WorkExpDto WorkExpDto) {
        return modelMapper.map(WorkExpDto, WorkExpEntity.class);
    }
}

