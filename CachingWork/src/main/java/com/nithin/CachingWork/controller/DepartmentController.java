package com.nithin.CachingWork.controller;

import com.nithin.CachingWork.entity.Department;
import com.nithin.CachingWork.entity.Employee;
import com.nithin.CachingWork.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department){
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.create(department));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long departmentId){
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getDepartment(departmentId));
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long departmentId,@RequestBody Department department){
        return ResponseEntity.ok(departmentService.updateDepartment(departmentId,department));
    }
}
