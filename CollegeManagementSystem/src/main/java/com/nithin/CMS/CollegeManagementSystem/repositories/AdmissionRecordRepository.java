package com.nithin.CMS.CollegeManagementSystem.repositories;

import com.nithin.CMS.CollegeManagementSystem.entities.AdmissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecord,Long> {
}
