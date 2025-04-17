package com.example.job_application_eval.mappers.impl;

import com.example.job_application_eval.dtos.ApplicantEnglishLevelDto;
import com.example.job_application_eval.dtos.JobPostingDto;
import com.example.job_application_eval.entities.ApplicantEnglishLevelEntity;
import com.example.job_application_eval.entities.JobPostingEntity;
import com.example.job_application_eval.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class JobPostingMapperImpl implements Mapper<JobPostingEntity, JobPostingDto> {
    private ModelMapper modelMapper;

    public JobPostingMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public JobPostingDto mapTo(JobPostingEntity jobPostingEntity) {
        return modelMapper.map(jobPostingEntity, JobPostingDto.class);
    }

    @Override
    public JobPostingEntity mapFrom(JobPostingDto jobPostingDto) {
        return modelMapper.map(jobPostingDto, JobPostingEntity.class);
    }
}