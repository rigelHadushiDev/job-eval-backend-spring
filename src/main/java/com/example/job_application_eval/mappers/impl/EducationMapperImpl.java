package com.example.job_application_eval.mappers.impl;

import com.example.job_application_eval.dtos.EducationDto;
import com.example.job_application_eval.entities.EducationEntity;
import com.example.job_application_eval.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EducationMapperImpl implements Mapper<EducationEntity, EducationDto> {
    private ModelMapper modelMapper;

    public EducationMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EducationDto mapTo(EducationEntity EducationEntity) {
        return modelMapper.map(EducationEntity, EducationDto.class);
    }

    @Override
    public EducationEntity mapFrom(EducationDto EducationDto) {
        return modelMapper.map(EducationDto, EducationEntity.class);
    }
}
