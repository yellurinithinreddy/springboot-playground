package com.nithin.TestingApp.repository;

import com.nithin.TestingApp.entity.Employee;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByEmail(@NotBlank(message = "email is required") String email);
}
