package com.nithin.springRestAPi.springbootweb.controllers;

import com.nithin.springRestAPi.springbootweb.dto.DepartmentDTO;
import com.nithin.springRestAPi.springbootweb.exceptions.ResourceNotFoundException;
import com.nithin.springRestAPi.springbootweb.services.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/depts")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepts(){
        List<DepartmentDTO> departmentEntities = departmentService.getAllDepts();

        return ResponseEntity.ok(departmentEntities);
    }
    @GetMapping("/{deptId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long deptId){
        Optional<DepartmentDTO> departmentDTO = departmentService.getDeptById(deptId);
        return departmentDTO.map(departmentDTO1 -> ResponseEntity.ok(departmentDTO1))
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Department not found with id: "+deptId);
                });
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO departmentDTO){
        return new ResponseEntity<>(departmentService.createDept(departmentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{deptId}")
    public  ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long deptId,@RequestBody @Valid DepartmentDTO departmentDTO){
        return ResponseEntity.ok(departmentService.updateDept(deptId,departmentDTO));
    }

    @DeleteMapping("/{deptId}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable Long deptId){
        return ResponseEntity.ok(departmentService.deleteDept(deptId));
    }

    @PatchMapping("/{deptId}")
    public ResponseEntity<DepartmentDTO> updatePartialDepartmentById(@PathVariable Long deptId, @RequestBody Map<String,Object> updates){
        return ResponseEntity.ok(departmentService.updatePartialDeptById(deptId,updates));
    }
}
