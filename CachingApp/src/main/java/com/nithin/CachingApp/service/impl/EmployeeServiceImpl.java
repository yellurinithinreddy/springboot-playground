package com.nithin.CachingApp.service.impl;


import com.nithin.CachingApp.dto.EmployeeDTO;
import com.nithin.CachingApp.entity.Employee;
import com.nithin.CachingApp.exception.ResourceNotFoundException;
import com.nithin.CachingApp.repository.EmployeeRepository;
import com.nithin.CachingApp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    @Cacheable(cacheNames = "employees",key = "#employeeId")
    public EmployeeDTO getEmployeeById(Long employeeId) {
        log.info("Trying to get employee by id: {}",employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> {
                    log.error("Employee not found with Id: {}",employeeId);
                    return new ResourceNotFoundException("Employee not found with Id: " + employeeId);
                });

        log.debug("Successfully retrieved employee with Id: {},{}",employeeId,employee);
        return modelMapper.map(employee,EmployeeDTO.class);
    }

    @Override
    @CachePut(cacheNames = "employees",key = "#result.id")
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.info("Trying to create a new employee");

        List<Employee> employees = employeeRepository.findByEmail(employeeDTO.getEmail());
        if(!employees.isEmpty()){
            log.error("Employee with email already exists: {}",employeeDTO.getEmail());
            throw new RuntimeException("Employee with email already exists: "+employeeDTO.getEmail());
        }
        Employee toBeSaved = modelMapper.map(employeeDTO,Employee.class);

        Employee savedEmployee = employeeRepository.save(toBeSaved);
        log.debug("Created new employee: {}",savedEmployee);
        return modelMapper.map(savedEmployee,EmployeeDTO.class);
    }

    @Override
    @CachePut(cacheNames = "employees",key = "#employeeId")
    public EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {
        log.info("Trying to update a employee with Id: {}",employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(
                        () -> {
                            log.error("Employee not found with Id: {}",employeeId);
                            return new ResourceNotFoundException("Employee not found with Id: " + employeeId);
                        }
                );

        if(!employeeDTO.getEmail().equals(employee.getEmail())){
            log.error("User cannot update the email from {} to {}",employee.getEmail(),employeeDTO.getEmail());
            throw new RuntimeException("User should not try to update email");
        }

        employeeDTO.setId(employeeId);
        modelMapper.map(employeeDTO,employee);
        Employee updatedEmployee = employeeRepository.save(employee);
        log.debug("Updated Employee with Id: {},{}",employeeId,employeeDTO);
        return modelMapper.map(updatedEmployee,EmployeeDTO.class);
    }

    @Override
    @CacheEvict(cacheNames = "employees",key = "#employeeId")
    public void deleteEmployee(Long employeeId) {
        log.info("Trying to delete a employee with Id: {}",employeeId);
        boolean exists = employeeRepository.existsById(employeeId);
        if(!exists){
            log.error("Employee not found with Id: {}",employeeId);
            throw new ResourceNotFoundException("Employee not found with Id: "+employeeId);
        }

        log.debug("Deleted a employee with Id: {}",employeeId);
        employeeRepository.deleteById(employeeId);
    }
}
