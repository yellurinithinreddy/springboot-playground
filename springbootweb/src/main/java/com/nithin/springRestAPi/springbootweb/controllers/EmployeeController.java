package com.nithin.springRestAPi.springbootweb.controllers;

import com.nithin.springRestAPi.springbootweb.dto.EmployeeDTO;
import com.nithin.springRestAPi.springbootweb.entities.EmployeeEntity;
import com.nithin.springRestAPi.springbootweb.repositories.EmployeeRepository;
import com.nithin.springRestAPi.springbootweb.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id){
        return employeeService.getEmployeeById(id);
    }

    @GetMapping
    List<EmployeeDTO> getAllEmployees(@RequestParam(required = false) Integer age, @RequestParam(required = false) String sortBy){
        return employeeService.getAllEmployees();
    }

    @PostMapping
    EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
        return employeeService.addEmployee(inputEmployee);
    }

    @PutMapping
    String updateEmployeeById(){
        return "Hello from PUT";
    }

}
