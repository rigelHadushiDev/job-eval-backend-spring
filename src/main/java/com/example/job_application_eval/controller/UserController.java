package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.ChangePasswordDto;
import com.example.job_application_eval.dtos.UserDto;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.entities.enums.Role;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.responses.GeneralSuccessfulResp;
import com.example.job_application_eval.service.UserService;
import com.example.job_application_eval.validation.OnCreateUser;
import com.example.job_application_eval.validation.OnEditUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/currentUser")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserDto currentUserDto = userService.getCurrentUser();
        return ResponseEntity.ok(currentUserDto);
    }

    @GetMapping("/listUsers")
    public ResponseEntity<Page<UserDto>> allUsers(
            @PageableDefault(page = 0, size = 10, sort = "username", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<UserDto> pageOfUsers = userService.allUsers(pageable);
        return ResponseEntity.ok(pageOfUsers);
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
        Page<UserDto> pageOfUsers = userService.findUsersByRoles(rolesToFetch, pageable);
        return ResponseEntity.ok(pageOfUsers);
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
        UserDto updatedUser = userService.editCurrUserData(userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/currUser")
    public ResponseEntity<UserDto> deleteCurrentUser() {
        UserDto deletedCurrUserAcc = userService.deleteYourUserAccount();
        return ResponseEntity.ok(deletedCurrUserAcc);
    }

    @DeleteMapping()
    public ResponseEntity<UserDto> deleteUser(@RequestParam("userId") Long userId) {
        UserDto updatedUser = userService.deleteUser(userId);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/searchUserFullName")
    public ResponseEntity<Page<UserDto>> searchUsersByFullName( @RequestParam("fullName") String fullName, Pageable pageable) {
        Page<UserDto> users = userService.searchUsersByFullName(fullName, pageable);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> save(@Validated(OnCreateUser.class) @RequestBody UserDto userDto) {
        UserDto savedUser = userService.save(userDto);
        return ResponseEntity.ok(savedUser);
    }

   @GetMapping("/getUser")
   public ResponseEntity<UserDto> getUser(@RequestParam("username") String username) {
       UserDto userDto = userService.getUserByUserName(username);
       return ResponseEntity.ok(userDto);
   }
}
