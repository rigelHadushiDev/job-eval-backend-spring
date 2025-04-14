package com.example.job_application_eval.controller;

import com.example.job_application_eval.dtos.EducationDto;
import com.example.job_application_eval.entities.EducationEntity;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.service.EducationService;
import com.example.job_application_eval.validation.OnEditEducation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;
    private final Mapper<EducationEntity, EducationDto> educationMapper;


    @DeleteMapping()
    public ResponseEntity<EducationDto> deleteEducation(@RequestParam Long educationId){
        EducationEntity deletedEducation = educationService.deleteEducation(educationId);
        return new ResponseEntity<>( educationMapper.mapTo(deletedEducation), HttpStatus.OK);
    }

    @GetMapping("/userEducations")
    public ResponseEntity<List<EducationDto>> findEducationsByUserId(@RequestParam Long userId) {
        List<EducationEntity> educationEntities = educationService.findEducationsByUserId(userId);
        List<EducationDto> educationDto = educationEntities.stream()
                .map(educationMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(educationDto, HttpStatus.OK);
    }

    @GetMapping("/getEducation")
    public ResponseEntity<EducationDto> getEducationById(@RequestParam Long educationId){
        EducationEntity extractedEducation = educationService.findEducationById(educationId);
        return new ResponseEntity<>( educationMapper.mapTo(extractedEducation), HttpStatus.OK);
    }


    @PostMapping("create")
    public ResponseEntity<EducationDto> createEducation(@RequestBody EducationDto educationDto){
        EducationEntity educationEntity = educationMapper.mapFrom(educationDto);
        EducationEntity updatedEducation = educationService.save(educationEntity);
        return new ResponseEntity<>( educationMapper.mapTo(updatedEducation), HttpStatus.OK);
    }

    @PutMapping("edit")
    public ResponseEntity<EducationDto> editEducation(@Validated(OnEditEducation.class) @RequestBody EducationDto educationDto){
        EducationEntity educationEntity = educationMapper.mapFrom(educationDto);
        EducationEntity updatedEducation = educationService.editEducation(educationEntity);
        return new ResponseEntity<>( educationMapper.mapTo(updatedEducation), HttpStatus.OK);
    }

}
