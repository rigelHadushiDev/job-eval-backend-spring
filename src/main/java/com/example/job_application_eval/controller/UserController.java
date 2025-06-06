package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.ChangePasswordDto;
import com.example.job_application_eval.dtos.UserDto;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.responses.GeneralSuccessfulResp;
import com.example.job_application_eval.service.UserService;
import com.example.job_application_eval.service.impl.UserServiceImpl;
import com.example.job_application_eval.validation.OnCreateUser;
import com.example.job_application_eval.validation.OnEditUser;
import com.example.job_application_eval.validation.OnSignUpUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;

    @GetMapping("/currentUser")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserEntity currentUserEntity = userService.getCurrentUserEntity();
        return new ResponseEntity<>(userMapper.mapTo(currentUserEntity), HttpStatus.OK);
    }

    @GetMapping("/listUsers")
    public ResponseEntity<Page<UserDto>> allUsers(
            @PageableDefault(page = 0, size = 10, sort = "username", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<UserEntity> pageOfUsers = userService.allUsers(pageable);
        Page<UserDto> pageOfUserDtos = pageOfUsers.map(userMapper::mapTo);
        return ResponseEntity.ok(pageOfUserDtos);
    }

    @GetMapping("/privilegedUsers")
    public ResponseEntity<Page<UserDto>> getAdminsAndRecruiters(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "username",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        List<Role> rolesToFetch = List.of(Role.ADMIN, Role.RECRUITER);
        Page<UserEntity> pageOfUsers = userService.findUsersByRoles(rolesToFetch, pageable);
        Page<UserDto> pageOfUserDtos = pageOfUsers.map(userMapper::mapTo);
        return ResponseEntity.ok(pageOfUserDtos);
    }


    @PatchMapping("/changePassw")
    public ResponseEntity<GeneralSuccessfulResp> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        GeneralSuccessfulResp changedUserPassword = userService.changeUserPassword(changePasswordDto);
        return new ResponseEntity<>(changedUserPassword, HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<UserDto> editCurrUserData(
            @Validated(OnEditUser.class)
            @RequestBody UserDto userDto
    ) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUser = userService.editCurrUserData(userEntity);
        return new ResponseEntity<>( userMapper.mapTo(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/currUser")
    public ResponseEntity<UserDto> deleteCurrentUser() {
        UserEntity deletedCurrUserAcc = userService.deleteYourUserAccount();
        return new ResponseEntity<>(userMapper.mapTo(deletedCurrUserAcc), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<UserDto> deleteUser(@RequestParam("userId") Long userId) {
        UserEntity updatedUser = userService.deleteUser(userId);
        return new ResponseEntity<>( userMapper.mapTo(updatedUser), HttpStatus.OK);
    }

    @GetMapping("/searchUserFullName")
    public Page<UserDto> searchUsersByFullName( @RequestParam("fullName") String fullName, Pageable pageable) {
        Page<UserEntity> patients = userService.searchUsersByFullName(fullName, pageable);
        return patients.map(userMapper::mapTo);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> save(@Validated(OnCreateUser.class) @RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUser = userService.save(userEntity);
        return new ResponseEntity<>( userMapper.mapTo(updatedUser), HttpStatus.OK);
    }

   @GetMapping("/getUser")
   public ResponseEntity<UserDto> getUser(@RequestParam("username") String username) {
       UserEntity userEntity = userService.getUserByUserName(username);
       return new ResponseEntity<>(userMapper.mapTo(userEntity), HttpStatus.OK);
   }
}
