package com.example.job_application_eval.mappers.impl;

import com.example.job_application_eval.dtos.JobApplicationDto;
import com.example.job_application_eval.dtos.JobPostingDto;
import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.entities.JobPostingEntity;
import com.example.job_application_eval.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationMapperImpl implements Mapper<JobApplicationEntity, JobApplicationDto> {
    private ModelMapper modelMapper;

    public JobApplicationMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public JobApplicationDto mapTo(JobApplicationEntity jobApplicationEntity) {
        return modelMapper.map(jobApplicationEntity, JobApplicationDto.class);
    }

    @Override
    public JobApplicationEntity mapFrom(JobApplicationDto jobApplicationDto) {
        return modelMapper.map(jobApplicationDto, JobApplicationEntity.class);
    }
}