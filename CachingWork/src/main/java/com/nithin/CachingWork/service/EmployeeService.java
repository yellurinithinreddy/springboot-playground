package com.nithin.CachingWork.service;

import com.nithin.CachingWork.entity.Employee;
import com.nithin.CachingWork.entity.SalaryAccount;
import com.nithin.CachingWork.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final SalaryAccountService salaryAccountService;

    @CachePut(cacheNames = "employees",key = "#result.id")
    public Employee create(Employee employee) {
        List<Employee> list = employeeRepository.findByEmail(employee.getEmail());

        if(!list.isEmpty()) throw new RuntimeException("Duplicate email found");

        Employee savedEmployee  =employeeRepository.save(employee);

        SalaryAccount salaryAccount = salaryAccountService.create(savedEmployee);

        return  savedEmployee;

    }

    @Cacheable(cacheNames = "employees",key = "#employeeId")
    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee Not found"));
    }


    @CachePut(cacheNames = "employees",key = "#employeeId")
    public Employee updateEmployee(Long employeeId,Employee employee) {
        Employee savedEmployee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee Not found"));
        employee.setId(savedEmployee.getId());
        modelMapper.map(employee,savedEmployee);
        return employeeRepository.save(savedEmployee);
    }
}
