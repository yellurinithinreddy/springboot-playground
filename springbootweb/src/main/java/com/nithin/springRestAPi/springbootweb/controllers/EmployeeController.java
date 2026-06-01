package com.nithin.springRestAPi.springbootweb.controllers;

import com.nithin.springRestAPi.springbootweb.dto.EmployeeDTO;
import com.nithin.springRestAPi.springbootweb.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long id){
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
        return employeeDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age, @RequestParam(required = false) String sortBy){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){
        return new ResponseEntity<>(employeeService.addEmployee(inputEmployee), HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    ResponseEntity<EmployeeDTO> updateEmployeeById(@PathVariable Long employeeId,@RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId,employeeDTO));
    }

    @PatchMapping("/{employeeId}")
    ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@PathVariable Long employeeId, @RequestBody Map<String,Object> updates){
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId,updates);
        if(employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

    @DeleteMapping("/{employeeId}")
    ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){
        boolean isDeleted = employeeService.removeEmployee(employeeId);
        if(!isDeleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(true);
    }

}
