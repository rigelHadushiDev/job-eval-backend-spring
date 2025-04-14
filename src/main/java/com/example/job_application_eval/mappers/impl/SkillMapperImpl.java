package com.example.job_application_eval.mappers.impl;

import com.example.job_application_eval.dtos.SkillDto;
import com.example.job_application_eval.entities.SkillEntity;
import com.example.job_application_eval.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SkillMapperImpl implements Mapper<SkillEntity, SkillDto> {
    private ModelMapper modelMapper;

    public SkillMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SkillDto mapTo(SkillEntity SkillEntity) {
        return modelMapper.map(SkillEntity, SkillDto.class);
    }

    @Override
    public SkillEntity mapFrom(SkillDto SkillDto) {
        return modelMapper.map(SkillDto, SkillEntity.class);
    }
}
