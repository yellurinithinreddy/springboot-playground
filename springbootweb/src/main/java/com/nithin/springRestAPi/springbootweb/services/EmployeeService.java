package com.nithin.springRestAPi.springbootweb.services;

import com.nithin.springRestAPi.springbootweb.dto.EmployeeDTO;
import com.nithin.springRestAPi.springbootweb.entities.EmployeeEntity;
import com.nithin.springRestAPi.springbootweb.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class))
                .toList();
    }


    public EmployeeDTO addEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toBeSaved = modelMapper.map(inputEmployee,EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(toBeSaved),EmployeeDTO.class);
    }
}
