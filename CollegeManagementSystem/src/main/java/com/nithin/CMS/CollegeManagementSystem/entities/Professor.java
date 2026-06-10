package com.nithin.CMS.CollegeManagementSystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "professor",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "professor_student",
            joinColumns = @JoinColumn(name="professor_id"),
            inverseJoinColumns = @JoinColumn(name="student_id")
    )
    private List<Student> students = new ArrayList<>();


}
