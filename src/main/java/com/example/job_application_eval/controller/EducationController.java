package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.EducationDto;
import com.example.job_application_eval.service.EducationService;
import com.example.job_application_eval.validation.OnEditEducation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @DeleteMapping()
    public ResponseEntity<EducationDto> deleteEducation(@RequestParam Long educationId){
        EducationDto deletedEducation = educationService.deleteEducation(educationId);
        return ResponseEntity.ok(deletedEducation);
    }

    @GetMapping("/userEducations")
    public ResponseEntity<List<EducationDto>> findEducationsByUserId(@RequestParam Long userId) {
        List<EducationDto> educationDtos = educationService.findEducationsByUserId(userId);
        return ResponseEntity.ok(educationDtos);
    }

    @GetMapping("/getEducation")
    public ResponseEntity<EducationDto> getEducationById(@RequestParam Long educationId){
        EducationDto extractedEducation = educationService.findEducationById(educationId);
        return ResponseEntity.ok(extractedEducation);
    }


    @PostMapping("create")
    public ResponseEntity<EducationDto> createEducation(@RequestBody EducationDto educationDto){
        EducationDto updatedEducation = educationService.save(educationDto);
        return ResponseEntity.ok(updatedEducation);
    }

    @PutMapping("edit")
    public ResponseEntity<EducationDto> editEducation(@Validated(OnEditEducation.class) @RequestBody EducationDto educationDto){
        EducationDto updatedEducation = educationService.editEducation(educationDto);
        return ResponseEntity.ok(updatedEducation);
    }
}
