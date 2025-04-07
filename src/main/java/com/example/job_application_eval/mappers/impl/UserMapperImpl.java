package com.example.job_application_eval.mappers.impl;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.dtos.UserDto;
import com.example.job_application_eval.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {
    private ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
