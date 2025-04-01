package com.example.job_application_eval.service;

import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }
}
