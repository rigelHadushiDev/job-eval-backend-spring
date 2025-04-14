package com.example.job_application_eval.config.utils;

import com.example.job_application_eval.entities.UserEntity;
import com.example.job_application_eval.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class Utils {

    private final UserRepository userRepository;

    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "userNotFound"));
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    public void assertCurrentUserOwns(Long entityOwnerId) {
        Long currentUserId = getCurrentUserId();
        if (!currentUserId.equals(entityOwnerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "notOwnerOfResource");
        }
    }
}