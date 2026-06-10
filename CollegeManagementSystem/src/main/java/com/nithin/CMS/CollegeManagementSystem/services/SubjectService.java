package com.nithin.CMS.CollegeManagementSystem.services;

import com.nithin.CMS.CollegeManagementSystem.dto.ProfessorDTO;
import com.nithin.CMS.CollegeManagementSystem.dto.SubjectDTO;
import com.nithin.CMS.CollegeManagementSystem.entities.Professor;
import com.nithin.CMS.CollegeManagementSystem.entities.Subject;
import com.nithin.CMS.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.nithin.CMS.CollegeManagementSystem.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    private final ModelMapper modelMapper;

    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        Subject subject = modelMapper.map(subjectDTO, Subject.class);
        Subject savedEntity = subjectRepository.save(subject);
        return modelMapper.map(savedEntity,SubjectDTO.class);
    }

    public List<SubjectDTO> getSubjects() {

        List<Subject> subjects = subjectRepository.findAll();

        return subjects.stream()
                .map(subject -> modelMapper.map(subject,SubjectDTO.class))
                .toList();
    }


    public SubjectDTO getSubjectById(Long subjectId) {
        isExistById(subjectId);
        Subject subject = subjectRepository.findById(subjectId).get();
        return modelMapper.map(subject,SubjectDTO.class);
    }

    public void isExistById(Long subjectId){
        boolean exists = subjectRepository.existsById(subjectId);
        if(!exists) throw new ResourceNotFoundException("Subject not found with id: "+subjectId);
    }

    public Boolean deleteSubject(Long subjectId) {
        isExistById(subjectId);
        subjectRepository.deleteById(subjectId);
        return true;
    }

    public SubjectDTO updateSubject(Long subjectId, SubjectDTO subjectDTO) {
        isExistById(subjectId);
        Subject subject = modelMapper.map(subjectDTO,Subject.class);
        subject.setId(subjectId);
        return modelMapper.map(subjectRepository.save(subject),SubjectDTO.class);


    }
}
