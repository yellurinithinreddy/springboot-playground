package com.nithin.springRestAPi.springbootweb.controllers;

import com.nithin.springRestAPi.springbootweb.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @GetMapping("/{employeeId}")
    EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id){
        return new EmployeeDTO(id,"Nithin","nithin@gmail.com",24, LocalDate.of(2024,11,05),true);
    }

    @GetMapping
    String getAllEmployees(@RequestParam(required = false) Integer age,@RequestParam(required = false) String sortBy){
        return "Hi my age is "+age+" "+sortBy;
    }

    @PostMapping
    EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO imputEmployee){
        imputEmployee.setId(101L);
        return imputEmployee;
    }

    @PutMapping
    String updateEmployeeById(){
        return "Hello from PUT";
    }

}
