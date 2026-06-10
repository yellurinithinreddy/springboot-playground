package com.nithin.CMS.CollegeManagementSystem.controllers;

import com.nithin.CMS.CollegeManagementSystem.dto.ProfessorDTO;
import com.nithin.CMS.CollegeManagementSystem.dto.StudentDTO;
import com.nithin.CMS.CollegeManagementSystem.dto.SubjectDTO;
import com.nithin.CMS.CollegeManagementSystem.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getAllProfessors(){
        return ResponseEntity.ok(professorService.getProfessors());
    }


    @PostMapping
    public ResponseEntity<ProfessorDTO> addProfessor(@RequestBody ProfessorDTO professorDTO){
        return new ResponseEntity<>(professorService.createProfessor(professorDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{professorId}")
    public ResponseEntity<ProfessorDTO> getProfessorByID(@PathVariable Long professorId){
        return ResponseEntity.ok(professorService.getProfessorById(professorId));
    }

    @DeleteMapping(path = "/{professorId}")
    public ResponseEntity<Boolean> deleteProfessorById(@PathVariable Long professorId){
        return ResponseEntity.ok(professorService.deleteProfessor(professorId));
    }

    @PutMapping(path = "/{professorId}")
    public ResponseEntity<ProfessorDTO> updateProfessorById(@PathVariable Long professorId,@RequestBody ProfessorDTO professorDTO){
        return  ResponseEntity.ok(professorService.updateProfessor(professorId,professorDTO));
    }


    @PostMapping("/{professorId}/students/{studentId}")
    public ResponseEntity<ProfessorDTO> assignStudentToProfessor(@PathVariable Long professorId,@PathVariable Long studentId){
        return ResponseEntity.ok(professorService.assignStudentToProfessor(professorId,studentId));
    }

    @PostMapping("/{professorId}/remstudents/{studentId}")
    public ResponseEntity<ProfessorDTO> removeStudentFromProfessor(@PathVariable Long professorId,@PathVariable Long studentId){
        return ResponseEntity.ok(professorService.removeStudentFromProfessor(professorId,studentId));
    }

    @GetMapping("/{professorId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsFromProfessor(@PathVariable Long professorId){
        return ResponseEntity.ok(professorService.getStudentsFromProfessor(professorId));
    }

}
