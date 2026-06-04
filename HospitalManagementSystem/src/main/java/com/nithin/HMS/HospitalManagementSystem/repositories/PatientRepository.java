package com.nithin.HMS.HospitalManagementSystem.repositories;


import com.nithin.HMS.HospitalManagementSystem.dto.BloodGroupStats;
import com.nithin.HMS.HospitalManagementSystem.dto.CPatientInfo;
import com.nithin.HMS.HospitalManagementSystem.dto.IPatientInfo;
import com.nithin.HMS.HospitalManagementSystem.entities.PatientEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity,Long> {


    @Query("select p.id as id,p.name as name,p.email as email from PatientEntity p")
    List<IPatientInfo> getAllPatientInfo();


    @Query("select new com.nithin.HMS.HospitalManagementSystem.dto.CPatientInfo(p.name,p.email) from PatientEntity p")
    List<CPatientInfo> getAllPatientInfoConcrete();


    @Query("select new com.nithin.HMS.HospitalManagementSystem.dto.BloodGroupStats(p.bloodGroup,COUNT(p)) from PatientEntity p group by p.bloodGroup order by COUNT(p)")
    List<BloodGroupStats> getBloodGroupStats();

    @Transactional
    @Modifying
    @Query("update PatientEntity p set p.name=:name where p.id=:id")
    int updatePatientName(@Param("name") String name,@Param("id") Long id);
}
