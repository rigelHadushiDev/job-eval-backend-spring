package com.example.job_application_eval.mappers.impl;

import com.example.job_application_eval.dtos.ApplicantEnglishLevelDto;
import com.example.job_application_eval.entities.ApplicantEnglishLevelEntity;
import com.example.job_application_eval.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ApplicantEnglishLevelMapperImpl implements Mapper<ApplicantEnglishLevelEntity, ApplicantEnglishLevelDto> {
    private ModelMapper modelMapper;

    public ApplicantEnglishLevelMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ApplicantEnglishLevelDto mapTo(ApplicantEnglishLevelEntity skillEntity) {
        return modelMapper.map(skillEntity, ApplicantEnglishLevelDto.class);
    }

    @Override
    public ApplicantEnglishLevelEntity mapFrom(ApplicantEnglishLevelDto skillEntity) {
        return modelMapper.map(skillEntity, ApplicantEnglishLevelEntity.class);
    }
}
