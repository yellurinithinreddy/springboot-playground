package com.nithin.springRestAPi.springbootweb.services;

import com.nithin.springRestAPi.springbootweb.dto.DepartmentDTO;
import com.nithin.springRestAPi.springbootweb.entities.DepartmentEntity;
import com.nithin.springRestAPi.springbootweb.entities.EmployeeEntity;
import com.nithin.springRestAPi.springbootweb.exceptions.ResourceNotFoundException;
import com.nithin.springRestAPi.springbootweb.repositories.DepartmentRepository;
import jakarta.validation.Valid;
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
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    
    private final ModelMapper modelMapper;
    
    public List<DepartmentDTO> getAllDepts() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return departmentEntities
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity,DepartmentDTO.class))
                .toList();
        
    }


    public Optional<DepartmentDTO> getDeptById(Long deptId){
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(deptId);
        return departmentEntity.map(departmentEntity1 -> modelMapper.map(departmentEntity1,DepartmentDTO.class));

    }

    public DepartmentDTO createDept(@Valid DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = modelMapper.map(departmentDTO,DepartmentEntity.class);
        return modelMapper.map(departmentRepository.save(departmentEntity),DepartmentDTO.class);
    }


    public DepartmentDTO updateDept(Long deptId, @Valid DepartmentDTO departmentDTO) {
        isExistsByDeptId(deptId);
        DepartmentEntity departmentEntity = modelMapper.map(departmentDTO,DepartmentEntity.class);
        departmentEntity.setId(deptId);
        DepartmentEntity savedEntity = departmentRepository.save(departmentEntity);
        return modelMapper.map(savedEntity,DepartmentDTO.class);
    }


    public Boolean deleteDept(Long deptId) {
        isExistsByDeptId(deptId);
        departmentRepository.deleteById(deptId);
        return true;
    }

    public void isExistsByDeptId(Long deptId){
        boolean exists = departmentRepository.existsById(deptId);
        if(!exists) throw new ResourceNotFoundException("Department not found with id: "+deptId);
    }


    public DepartmentDTO updatePartialDeptById(Long deptId, Map<String, Object> updates) {
        isExistsByDeptId(deptId);
        DepartmentEntity departmentEntity = departmentRepository.findById(deptId).get();
        updates.forEach(
                (key,value) -> {
                    Field fieldToBeUpdated = ReflectionUtils.findField(DepartmentEntity.class,key);
                    fieldToBeUpdated.setAccessible(true);
                    ReflectionUtils.setField(fieldToBeUpdated,departmentEntity,value);
                }
        );


        return modelMapper.map(departmentRepository.save(departmentEntity),DepartmentDTO.class);

    }
}
