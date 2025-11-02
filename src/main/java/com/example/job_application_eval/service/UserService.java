package com.example.job_application_eval.service;


import com.example.job_application_eval.dtos.ChangePasswordDto;
import com.example.job_application_eval.dtos.UserDto;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.responses.GeneralSuccessfulResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {


    Page<UserDto> allUsers(Pageable pageable);

    Page<UserDto> findUsersByRoles(List<Role> roles, Pageable pageable);

    UserDto getCurrentUser();

    GeneralSuccessfulResp changeUserPassword(ChangePasswordDto changePasswordDto);

    UserDto deleteYourUserAccount();

    UserDto deleteUser(Long userId);

    UserDto editCurrUserData(UserDto userDto);

    Page<UserDto> searchUsersByFullName(String fullName, Pageable pageable);

    UserDto save(UserDto userDto);

    UserDto getUserByUserName(String username);
}