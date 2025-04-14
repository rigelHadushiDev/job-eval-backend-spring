package com.example.job_application_eval.controller;
import com.example.job_application_eval.dtos.SkillDto;
import com.example.job_application_eval.entities.SkillEntity;
import com.example.job_application_eval.mappers.Mapper;
import com.example.job_application_eval.service.SkillService;
import com.example.job_application_eval.validation.OnEditSkills;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("skill")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;
    private final Mapper<SkillEntity, SkillDto> mapper;
    

    @DeleteMapping()
    public ResponseEntity<SkillDto> deleteSkill(@RequestParam Long skillId){
        SkillEntity deletedSkill = skillService.deleteSkill(skillId);
        return new ResponseEntity<>( mapper.mapTo(deletedSkill), HttpStatus.OK);
    }

    @GetMapping("/userSkills")
    public ResponseEntity<List<SkillDto>> findSkillsByUserId(@RequestParam Long userId) {
        List<SkillEntity> skillEntities = skillService.findSkillsByUserId(userId);
        List<SkillDto> skillDto = skillEntities.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(skillDto, HttpStatus.OK);
    }

    @GetMapping("/getSkill")
    public ResponseEntity<SkillDto> getSkillById(@RequestParam Long skillId){
        SkillEntity extractedSkill = skillService.findSkillById(skillId);
        return new ResponseEntity<>( mapper.mapTo(extractedSkill), HttpStatus.OK);
    }


    @PostMapping("create")
    public ResponseEntity<SkillDto> createSkill(@Valid @RequestBody SkillDto skillDto){
        SkillEntity skillEntity = mapper.mapFrom(skillDto);
        SkillEntity createdSkill = skillService.save(skillEntity);
        return new ResponseEntity<>( mapper.mapTo(createdSkill), HttpStatus.OK);
    }

    @PutMapping("edit")
    public ResponseEntity<SkillDto> editSkill(@Validated(OnEditSkills.class) @RequestBody SkillDto skillDto){
        SkillEntity skillEntity = mapper.mapFrom(skillDto);
        SkillEntity updatedSkill = skillService.editSkillEntity(skillEntity);
        return new ResponseEntity<>( mapper.mapTo(updatedSkill), HttpStatus.OK);
    }
}
