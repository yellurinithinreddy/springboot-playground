package com.nithin.springRestAPi.springbootweb.repositories;

import com.nithin.springRestAPi.springbootweb.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long> {
}
