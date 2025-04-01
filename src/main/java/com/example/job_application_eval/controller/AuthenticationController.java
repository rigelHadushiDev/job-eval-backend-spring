package com.example.job_application_eval.controller;


import com.example.job_application_eval.dto.LoginUserDto;
import com.example.job_application_eval.dto.RegisterUserDto;
import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.responses.LogInResponse;
import com.example.job_application_eval.service.JwtService;
import com.example.job_application_eval.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterUserDto registerUserDto) {
        UserEntity registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponse> authenticate(@RequestBody LoginUserDto loginUserDto){
        UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LogInResponse loginResponse = new LogInResponse(jwtToken, jwtService.getExpirationTime(), authenticatedUser.getRole());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}