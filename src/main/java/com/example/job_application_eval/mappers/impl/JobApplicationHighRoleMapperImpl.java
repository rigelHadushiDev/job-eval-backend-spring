package com.example.job_application_eval.mappers.impl;

import com.example.job_application_eval.dtos.JobApplicationHighRoleDto;
import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationHighRoleMapperImpl implements Mapper<JobApplicationEntity , JobApplicationHighRoleDto> {
    private ModelMapper modelMapper;

    public JobApplicationHighRoleMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public JobApplicationHighRoleDto mapTo(JobApplicationEntity jobApplicationEntity) {
        return modelMapper.map(jobApplicationEntity, JobApplicationHighRoleDto.class);
    }

    @Override
    public JobApplicationEntity mapFrom(JobApplicationHighRoleDto jobApplicationHighRoleDto) {
        return modelMapper.map(jobApplicationHighRoleDto, JobApplicationEntity.class);
    }
}
