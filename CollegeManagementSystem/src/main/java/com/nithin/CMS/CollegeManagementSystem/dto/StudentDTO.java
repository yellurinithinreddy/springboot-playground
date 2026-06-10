package com.nithin.CMS.CollegeManagementSystem.dto;

import com.nithin.CMS.CollegeManagementSystem.entities.Professor;
import com.nithin.CMS.CollegeManagementSystem.entities.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private Long id;

    private String name;

    private List<Professor> professors;

    private List<Subject> subjects;
}
