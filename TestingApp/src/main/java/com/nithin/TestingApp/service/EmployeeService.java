package com.nithin.TestingApp.service;

import com.nithin.TestingApp.dto.EmployeeDTO;

public interface EmployeeService {
    
    EmployeeDTO getEmployeeById(Long employeeId);

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employeeDTO);

    void deleteEmployee(Long employeeId);
}
