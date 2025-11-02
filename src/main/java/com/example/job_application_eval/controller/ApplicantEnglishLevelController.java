package com.example.job_application_eval.controller;


import com.example.job_application_eval.dtos.ApplicantEnglishLevelDto;
import com.example.job_application_eval.entities.ApplicantEnglishLevelEntity;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.service.ApplicantEnglishLevelService;
import com.example.job_application_eval.validation.OnEditLanguage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("applicantEnglishLevel")
@RequiredArgsConstructor
public class ApplicantEnglishLevelController {

    private final ApplicantEnglishLevelService applicantEnglishLevelService;
    
    @DeleteMapping()
    public ResponseEntity<ApplicantEnglishLevelDto> deleteApplicantEnglishLevel(@RequestParam Long applicantEnglishLevelId) {
        ApplicantEnglishLevelDto applicantEnglishLevelDto = applicantEnglishLevelService.deleteApplicantEnglishLevel(applicantEnglishLevelId);
        return ResponseEntity.ok(applicantEnglishLevelDto);
    }

    @GetMapping("/getApplicantEnglishLevel")
    public ResponseEntity<ApplicantEnglishLevelDto> findApplicantEnglishLevelByUserId(@RequestParam Long userId) {
        ApplicantEnglishLevelDto applicantEnglishLevelDto = applicantEnglishLevelService.findApplicantEnglishLevelByUserId(userId);
        return ResponseEntity.ok(applicantEnglishLevelDto);
    }

    @PostMapping("create")
    public ResponseEntity<ApplicantEnglishLevelDto> createUserLanguage(@Valid @RequestBody ApplicantEnglishLevelDto applicantEnglishLevelDto) {
        ApplicantEnglishLevelDto createdApplicantEnglishLvl = applicantEnglishLevelService.save(applicantEnglishLevelDto);
        return ResponseEntity.ok(createdApplicantEnglishLvl);
    }

    @PutMapping("edit")
    public ResponseEntity<ApplicantEnglishLevelDto> editUserLanguage(@Validated(OnEditLanguage.class) @RequestBody ApplicantEnglishLevelDto applicantEnglishLevelDto) {
        ApplicantEnglishLevelDto editedApplicantEnglishLevel = applicantEnglishLevelService.editApplicantEnglishLevel(applicantEnglishLevelDto);
        return ResponseEntity.ok(editedApplicantEnglishLevel);
    }
}