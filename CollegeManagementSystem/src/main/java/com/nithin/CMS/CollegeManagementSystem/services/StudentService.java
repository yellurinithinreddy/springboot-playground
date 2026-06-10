package com.nithin.CMS.CollegeManagementSystem.services;

import com.nithin.CMS.CollegeManagementSystem.dto.ProfessorDTO;
import com.nithin.CMS.CollegeManagementSystem.dto.StudentDTO;
import com.nithin.CMS.CollegeManagementSystem.entities.Professor;
import com.nithin.CMS.CollegeManagementSystem.entities.Student;
import com.nithin.CMS.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.nithin.CMS.CollegeManagementSystem.repositories.ProfessorRepository;
import com.nithin.CMS.CollegeManagementSystem.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final ModelMapper modelMapper;

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO,Student.class);
        Student savedEntity = studentRepository.save(student);
        return modelMapper.map(savedEntity,StudentDTO.class);
    }

    public List<StudentDTO> getStudents() {

        List<Student> professors = studentRepository.findAll();

        return professors.stream()
                .map(student -> modelMapper.map(student,StudentDTO.class))
                .toList();
    }


    public StudentDTO getStudentById(Long studentId) {
        isExistById(studentId);
        Student student = studentRepository.findById(studentId).get();
        return modelMapper.map(student,StudentDTO.class);
    }

    public void isExistById(Long studentId){
        boolean exists = studentRepository.existsById(studentId);
        if(!exists) throw new ResourceNotFoundException("Professor not found with id: "+studentId);
    }

    public Boolean deleteStudent(Long studentId) {
        isExistById(studentId);
        studentRepository.deleteById(studentId);
        return true;
    }

    public StudentDTO updateStudent(Long studentId, StudentDTO studentDTO) {
        isExistById(studentId);
        Student student = modelMapper.map(studentDTO,Student.class);
        student.setId(studentId);
        return modelMapper.map(studentRepository.save(student),StudentDTO.class);


    }
}
