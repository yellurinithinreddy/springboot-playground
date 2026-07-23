package com.nithin.CachingWork.service;

import com.nithin.CachingWork.entity.Department;
import com.nithin.CachingWork.entity.Employee;
import com.nithin.CachingWork.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    @CachePut(cacheNames = "departments",key = "#result.id")
    public Department create(Department department) {
        List<Department> list = departmentRepository.findByDepartmentName(department.getDepartmentName());

        if(!list.isEmpty()) throw new RuntimeException("Duplicate Department names");

        return departmentRepository.save(department);
    }


    @Cacheable(cacheNames = "departments",key = "#departmentId")
    public Department getDepartment(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    @CachePut(cacheNames = "departments",key = "#departmentId")
    public Department updateDepartment(Long departmentId, Department department) {
        Department savedDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setId(savedDepartment.getId());
        modelMapper.map(department,savedDepartment);
        return departmentRepository.save(savedDepartment);
    }
}
