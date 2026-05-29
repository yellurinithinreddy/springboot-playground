package com.nithin.springRestAPi.springbootweb.services;

import com.nithin.springRestAPi.springbootweb.dto.EmployeeDTO;
import com.nithin.springRestAPi.springbootweb.entities.EmployeeEntity;
import com.nithin.springRestAPi.springbootweb.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1,EmployeeDTO.class));
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

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {

        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);


    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        boolean isExists = existsByEmployeeId(employeeId);
        if(!isExists) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach(
                (key,value) ->{
                    Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class,key);
                    fieldToBeUpdated.setAccessible(true);
                    ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);

                }
        );
        employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }

    public boolean removeEmployee(Long employeeId) {
        boolean isExists = existsByEmployeeId(employeeId);
        if(!isExists) return false;
        employeeRepository.deleteById(employeeId);
        return true;
    }

    boolean existsByEmployeeId(Long employeeId){
        return employeeRepository.existsById(employeeId);
    }
}
