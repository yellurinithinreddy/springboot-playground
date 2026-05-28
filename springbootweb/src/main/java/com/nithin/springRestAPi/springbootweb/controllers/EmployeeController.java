package com.nithin.springRestAPi.springbootweb.controllers;

import com.nithin.springRestAPi.springbootweb.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @GetMapping("/{employeeId}")
    EmployeeDTO getEmployeeById(@PathVariable Long employeeId){
        return new EmployeeDTO(employeeId,"Nithin","nithin@gmail.com",24, LocalDate.of(2024,11,05),true);
    }

    @GetMapping
    String getAllEmployees(@RequestParam(required = false) Integer age){
        return "Hi my age is "+age;
    }

}
