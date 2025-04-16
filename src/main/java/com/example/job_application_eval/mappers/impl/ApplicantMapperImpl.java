package com.example.job_application_eval.mappers.impl;

import com.example.job_application_eval.dtos.ApplicantLanguageDto;
import com.example.job_application_eval.entities.ApplicantLanguageEntity;
import com.example.job_application_eval.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ApplicantMapperImpl implements Mapper<ApplicantLanguageEntity, ApplicantLanguageDto> {
    private ModelMapper modelMapper;

    public ApplicantMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ApplicantLanguageDto mapTo(ApplicantLanguageEntity skillEntity) {
        return modelMapper.map(skillEntity, ApplicantLanguageDto.class);
    }

    @Override
    public ApplicantLanguageEntity mapFrom(ApplicantLanguageDto skillEntity) {
        return modelMapper.map(skillEntity, ApplicantLanguageEntity.class);
    }
}
