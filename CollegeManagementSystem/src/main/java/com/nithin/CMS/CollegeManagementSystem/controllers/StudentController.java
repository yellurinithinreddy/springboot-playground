package com.nithin.CMS.CollegeManagementSystem.controllers;

import com.nithin.CMS.CollegeManagementSystem.dto.ProfessorDTO;
import com.nithin.CMS.CollegeManagementSystem.dto.StudentDTO;
import com.nithin.CMS.CollegeManagementSystem.services.ProfessorService;
import com.nithin.CMS.CollegeManagementSystem.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        return ResponseEntity.ok(studentService.getStudents());
    }


    @PostMapping
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO){
        return new ResponseEntity<>(studentService.createStudent(studentDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{studentId}")
    public ResponseEntity<StudentDTO> getStudentByID(@PathVariable Long studentId){
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    @DeleteMapping(path = "/{studentId}")
    public ResponseEntity<Boolean> deleteStudentById(@PathVariable Long studentId){
        return ResponseEntity.ok(studentService.deleteStudent(studentId));
    }

    @PutMapping(path = "/{studentId}")
    public ResponseEntity<StudentDTO> updateStudentById(@PathVariable Long studentId,@RequestBody StudentDTO studentDTO){
        return  ResponseEntity.ok(studentService.updateStudent(studentId,studentDTO));
    }
}
