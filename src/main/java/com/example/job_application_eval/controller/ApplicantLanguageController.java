package com.example.job_application_eval.controller;


import com.example.job_application_eval.dtos.ApplicantLanguageDto;
import com.example.job_application_eval.entities.ApplicantLanguageEntity;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.service.ApplicantLanguageService;
import com.example.job_application_eval.validation.OnEditLanguage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("applicantLanguage")
@RequiredArgsConstructor
public class ApplicantLanguageController {

    private final ApplicantLanguageService applicantLanguageService;
    private final Mapper<ApplicantLanguageEntity, ApplicantLanguageDto> mapper;

    @DeleteMapping()
    public ResponseEntity<ApplicantLanguageDto> deleteApplicantLanguage(@RequestParam Long applicantLanguageId) {
        ApplicantLanguageEntity deletedUserLanguage = applicantLanguageService.deleteApplicantLanguage(applicantLanguageId);
        return new ResponseEntity<>(mapper.mapTo(deletedUserLanguage), HttpStatus.OK);
    }

    @GetMapping("/getApplicantLanguages")
    public ResponseEntity<List<ApplicantLanguageDto>> findUserLanguagesById(@RequestParam Long userId) {
        List<ApplicantLanguageEntity> userLanguagesEntities = applicantLanguageService.findApplicantLanguagesByUserId(userId);
        List<ApplicantLanguageDto> languages = userLanguagesEntities.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(languages, HttpStatus.OK);
    }

    @GetMapping("/getApplicantLanguage")
    public ResponseEntity<ApplicantLanguageDto> getApplicantLanguagesById(@RequestParam Long applicantLanguageId) {
        ApplicantLanguageEntity extractedLanguage = applicantLanguageService.findApplicantLanguageById(applicantLanguageId);
        return new ResponseEntity<>(mapper.mapTo(extractedLanguage), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<ApplicantLanguageDto> createUserLanguage(@Valid @RequestBody ApplicantLanguageDto applicantLanguageDto) {
        ApplicantLanguageEntity applicantLanguageEntity = mapper.mapFrom(applicantLanguageDto);
        ApplicantLanguageEntity createdUserLanguage = applicantLanguageService.save(applicantLanguageEntity);
        return new ResponseEntity<>(mapper.mapTo(createdUserLanguage), HttpStatus.OK);
    }

    @PutMapping("edit")
    public ResponseEntity<ApplicantLanguageDto> editUserLanguage(@Validated(OnEditLanguage.class) @RequestBody ApplicantLanguageDto applicantLanguageDto) {
        ApplicantLanguageEntity applicantLanguageEntity = mapper.mapFrom(applicantLanguageDto);
        ApplicantLanguageEntity updatedUserLanguage = applicantLanguageService.editApplicantLanguage(applicantLanguageEntity);
        return new ResponseEntity<>(mapper.mapTo(updatedUserLanguage), HttpStatus.OK);
    }
}