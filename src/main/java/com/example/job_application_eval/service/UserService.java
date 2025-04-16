package com.example.job_application_eval.service;


import com.example.job_application_eval.dtos.ChangePasswordDto;
import com.example.job_application_eval.dtos.UserDto;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.responses.GeneralSuccessfulResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserEntity> allUsers();

    UserEntity getCurrentUserEntity();

    GeneralSuccessfulResp changeUserPassword(ChangePasswordDto changePasswordDto);

    UserEntity deleteYourUserAccount();

    UserEntity deleteUser(Long userId);

    UserEntity editCurrUserData(UserEntity userEntity);

    Page<UserEntity> searchUsersByFullName(String fullName, Pageable pageable);

    UserEntity save(UserEntity userEntity);

    UserEntity getUserByUserName(String username);
}