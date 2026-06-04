package com.nithin.HMS.HospitalManagementSystem.repositories;

import com.nithin.HMS.HospitalManagementSystem.dto.IPatientInfo;
import com.nithin.HMS.HospitalManagementSystem.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity,Long> {


    @Query("select p.id as id,p.name as name,p.email as email from PatientEntity p")
    List<IPatientInfo> getAllPatientInfo();
}
