package com.nithin.CachingApp.service;


import com.nithin.CachingApp.dto.EmployeeDTO;

public interface EmployeeService {
    
    EmployeeDTO getEmployeeById(Long employeeId);

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employeeDTO);

    void deleteEmployee(Long employeeId);
}
