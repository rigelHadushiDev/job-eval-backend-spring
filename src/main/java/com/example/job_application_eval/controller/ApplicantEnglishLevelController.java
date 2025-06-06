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
    private final Mapper<ApplicantEnglishLevelEntity, ApplicantEnglishLevelDto> mapper;

    @DeleteMapping()
    public ResponseEntity<ApplicantEnglishLevelDto> deleteApplicantEnglishLevel(@RequestParam Long applicantEnglishLevelId) {
        ApplicantEnglishLevelEntity applicantEnglishLevelEntity = applicantEnglishLevelService.deleteApplicantEnglishLevel(applicantEnglishLevelId);
        return new ResponseEntity<>(mapper.mapTo(applicantEnglishLevelEntity), HttpStatus.OK);
    }

    @GetMapping("/getApplicantEnglishLevel")
    public ResponseEntity<ApplicantEnglishLevelDto> findApplicantEnglishLevelByUserId(@RequestParam Long userId) {
        ApplicantEnglishLevelEntity applicantEnglishLevelEntity = applicantEnglishLevelService.findApplicantEnglishLevelByUserId(userId);
        if (applicantEnglishLevelEntity == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(mapper.mapTo(applicantEnglishLevelEntity), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<ApplicantEnglishLevelDto> createUserLanguage(@Valid @RequestBody ApplicantEnglishLevelDto applicantEnglishLevelDto) {
        ApplicantEnglishLevelEntity applicantEnglishLevelEntity = mapper.mapFrom(applicantEnglishLevelDto);
        ApplicantEnglishLevelEntity createdUserLanguage = applicantEnglishLevelService.save(applicantEnglishLevelEntity);
        return new ResponseEntity<>(mapper.mapTo(createdUserLanguage), HttpStatus.OK);
    }

    @PutMapping("edit")
    public ResponseEntity<ApplicantEnglishLevelDto> editUserLanguage(@Validated(OnEditLanguage.class) @RequestBody ApplicantEnglishLevelDto applicantEnglishLevelDto) {
        ApplicantEnglishLevelEntity applicantEnglishLevelEntity = mapper.mapFrom(applicantEnglishLevelDto);
        ApplicantEnglishLevelEntity updatedUserLanguage = applicantEnglishLevelService.editApplicantEnglishLevel(applicantEnglishLevelEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedUserLanguage), HttpStatus.OK);
    }
}