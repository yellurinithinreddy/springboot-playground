package com.nithin.CMS.CollegeManagementSystem.dto;

import com.nithin.CMS.CollegeManagementSystem.entities.Professor;
import com.nithin.CMS.CollegeManagementSystem.entities.Student;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private Long id;

    @NotBlank(message = " subject name should not be blank")
    private String name;

    private Professor professor;

    private List<Student> students;
}
