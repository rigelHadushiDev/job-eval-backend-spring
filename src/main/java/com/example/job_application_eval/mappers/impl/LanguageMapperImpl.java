package com.example.job_application_eval.mappers.impl;

import com.example.job_application_eval.dtos.UserLanguageDto;
import com.example.job_application_eval.entities.UserLanguageEntity;
import com.example.job_application_eval.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LanguageMapperImpl implements Mapper<UserLanguageEntity, UserLanguageDto> {
    private ModelMapper modelMapper;

    public LanguageMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserLanguageDto mapTo(UserLanguageEntity skillEntity) {
        return modelMapper.map(skillEntity, UserLanguageDto.class);
    }

    @Override
    public UserLanguageEntity mapFrom(UserLanguageDto skillEntity) {
        return modelMapper.map(skillEntity, UserLanguageEntity.class);
    }
}
