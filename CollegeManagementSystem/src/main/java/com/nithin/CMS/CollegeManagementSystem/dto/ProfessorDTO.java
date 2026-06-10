package com.nithin.CMS.CollegeManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nithin.CMS.CollegeManagementSystem.entities.Student;
import com.nithin.CMS.CollegeManagementSystem.entities.Subject;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDTO {

    private Long id;

    @NotBlank(message = "professor name should not be blank")
    private String name;


    private List<Subject> subjects = new ArrayList<>();

    private List<Student> students = new ArrayList<>();

}
