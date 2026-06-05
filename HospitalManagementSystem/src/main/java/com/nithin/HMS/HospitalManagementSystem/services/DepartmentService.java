package com.nithin.HMS.HospitalManagementSystem.services;

import com.nithin.HMS.HospitalManagementSystem.entities.Department;
import com.nithin.HMS.HospitalManagementSystem.entities.Doctor;
import com.nithin.HMS.HospitalManagementSystem.repositories.DepartmentRepository;
import com.nithin.HMS.HospitalManagementSystem.repositories.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public Department addDepartment(Department department, Long headDoctorId, List<Long> doctorsIds){
        Doctor headDoctor = doctorRepository.findById(headDoctorId).orElseThrow();
        department.setHeadDoctor(headDoctor);

        List<Doctor> doctors = doctorRepository.findAllById(doctorsIds);

        department.setDoctors(doctors);

        departmentRepository.save(department);
        return department;

    }


    @Transactional
    public void deleteDepartment(Long deptId){
        departmentRepository.findById(deptId).orElseThrow();
        departmentRepository.deleteById(deptId);
    }

}
