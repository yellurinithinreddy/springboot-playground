package com.nithin.CMS.CollegeManagementSystem.services;

import com.nithin.CMS.CollegeManagementSystem.dto.ProfessorDTO;
import com.nithin.CMS.CollegeManagementSystem.dto.StudentDTO;
import com.nithin.CMS.CollegeManagementSystem.dto.SubjectDTO;
import com.nithin.CMS.CollegeManagementSystem.entities.Professor;
import com.nithin.CMS.CollegeManagementSystem.entities.Student;
import com.nithin.CMS.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.nithin.CMS.CollegeManagementSystem.repositories.ProfessorRepository;
import com.nithin.CMS.CollegeManagementSystem.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    private final StudentRepository studentRepository;

    private final ModelMapper modelMapper;

    public ProfessorDTO createProfessor(ProfessorDTO professor) {
        Professor professor1 = modelMapper.map(professor,Professor.class);
        Professor savedEntity = professorRepository.save(professor1);
        return modelMapper.map(savedEntity,ProfessorDTO.class);
    }

    @Transactional
    public List<ProfessorDTO> getProfessors() {

        List<Professor> professors = professorRepository.findAll();

        return professors.stream()
                .map(professor -> modelMapper.map(professor,ProfessorDTO.class))
                .toList();
    }


    @Transactional
    public ProfessorDTO getProfessorById(Long professorId) {
        isExistById(professorId);
        Professor professor = professorRepository.findById(professorId).get();
        return modelMapper.map(professor,ProfessorDTO.class);
    }

    @Transactional
    public void isExistById(Long professorId){
        boolean exists = professorRepository.existsById(professorId);
        if(!exists) throw new ResourceNotFoundException("Professor not found with id: "+professorId);
    }

    @Transactional
    public Boolean deleteProfessor(Long professorId) {
        isExistById(professorId);
        professorRepository.deleteById(professorId);
        return true;
    }

    @Transactional
    public ProfessorDTO updateProfessor(Long professorId, ProfessorDTO professorDTO) {
        isExistById(professorId);
        Professor professor = modelMapper.map(professorDTO,Professor.class);
        professor.setId(professorId);
        return modelMapper.map(professorRepository.save(professor),ProfessorDTO.class);


    }

    @Transactional
    public ProfessorDTO assignStudentToProfessor(Long professorId, Long studentId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> {
            throw new ResourceNotFoundException("professor not found with id: "+professorId);
        });

        Student student = studentRepository.findById(studentId).orElseThrow(() -> {
            throw new ResourceNotFoundException("student not found with id: "+studentId);
        });

        professor.getStudents().add(student);
        student.getProfessors().add(professor);

        return modelMapper.map(professor,ProfessorDTO.class);

    }

    @Transactional
    public ProfessorDTO removeStudentFromProfessor(Long professorId, Long studentId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> {
            throw new ResourceNotFoundException("professor not found with id: "+professorId);
        });

        Student student = studentRepository.findById(studentId).orElseThrow(() -> {
            throw new ResourceNotFoundException("student not found with id: "+studentId);
        });


        int ind = 0;
        int f = 0;
        for(Student s:professor.getStudents()){
            if(s.getId() == studentId) {
                f=1;
                break;
            }
            else ind++;
        }

        if(f == 0) return modelMapper.map(professor,ProfessorDTO.class);
        professor.getStudents().remove(ind);
        student.getProfessors().remove(professor);
        return modelMapper.map(professor,ProfessorDTO.class);



    }


    @Transactional
    public List<StudentDTO> getStudentsFromProfessor(Long professorId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> {
            throw new ResourceNotFoundException("professor not found with id: "+professorId);
        });

        return professor.getStudents()
                .stream()
                .map(student -> modelMapper.map(student,StudentDTO.class))
                .toList();
    }
}
