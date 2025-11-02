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
    

    @DeleteMapping()
    public ResponseEntity<SkillDto> deleteSkill(@RequestParam Long skillId){
        SkillDto deletedSkill = skillService.deleteSkill(skillId);
        return ResponseEntity.ok(deletedSkill);
    }

    @GetMapping("/userSkills")
    public ResponseEntity<List<SkillDto>> findSkillsByUserId(@RequestParam Long userId) {
        List<SkillDto> skills = skillService.findSkillsByUserId(userId);
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/getSkill")
    public ResponseEntity<SkillDto> getSkillById(@RequestParam Long skillId){
        SkillDto extractedSkill = skillService.findSkillById(skillId);
        return ResponseEntity.ok(extractedSkill);
    }


    @PostMapping("create")
    public ResponseEntity<SkillDto> createSkill(@Valid @RequestBody SkillDto skillDto){
        SkillDto createdSkill = skillService.save(skillDto);
        return ResponseEntity.ok(createdSkill);
    }

    @PutMapping("edit")
    public ResponseEntity<SkillDto> editSkill(@Validated(OnEditSkills.class) @RequestBody SkillDto skillDto){
        SkillDto updatedSkill = skillService.editSkillEntity(skillDto);
        return ResponseEntity.ok(updatedSkill);
    }
}
