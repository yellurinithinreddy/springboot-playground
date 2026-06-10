package com.nithin.CMS.CollegeManagementSystem.controllers;

import com.nithin.CMS.CollegeManagementSystem.dto.ProfessorDTO;
import com.nithin.CMS.CollegeManagementSystem.dto.SubjectDTO;
import com.nithin.CMS.CollegeManagementSystem.services.ProfessorService;
import com.nithin.CMS.CollegeManagementSystem.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAllSubjects(){
        return ResponseEntity.ok(subjectService.getSubjects());
    }


    @PostMapping
    public ResponseEntity<SubjectDTO> addSubject(@RequestBody SubjectDTO subjectDTO){
        return new ResponseEntity<>(subjectService.createSubject(subjectDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{subjectId}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long subjectId){
        return ResponseEntity.ok(subjectService.getSubjectById(subjectId));
    }

    @DeleteMapping(path = "/{subjectId}")
    public ResponseEntity<Boolean> deleteSubjectById(@PathVariable Long subjectId){
        return ResponseEntity.ok(subjectService.deleteSubject(subjectId));
    }

    @PutMapping(path = "/{subjectId}")
    public ResponseEntity<SubjectDTO> updateSubjectById(@PathVariable Long subjectId,@RequestBody SubjectDTO subjectDTO){
        return  ResponseEntity.ok(subjectService.updateSubject(subjectId,subjectDTO));
    }
}
