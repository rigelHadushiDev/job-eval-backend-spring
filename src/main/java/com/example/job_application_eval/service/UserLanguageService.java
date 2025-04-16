package com.example.job_application_eval.service;


import com.example.job_application_eval.entities.UserLanguageEntity;

import java.util.List;

public interface UserLanguageService {

    UserLanguageEntity deleteUserLanguage(Long userLanguageId);

    List<UserLanguageEntity> findUserLanguagesByUserId(Long userId);

    UserLanguageEntity editUserLanguage(UserLanguageEntity userLanguageEntity);

    UserLanguageEntity save(UserLanguageEntity userLanguageEntity);

    UserLanguageEntity findUserLanguageById(Long userLanguageId);
}

