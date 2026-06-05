package com.nithin.HMS.HospitalManagementSystem.repositories;

import com.nithin.HMS.HospitalManagementSystem.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
