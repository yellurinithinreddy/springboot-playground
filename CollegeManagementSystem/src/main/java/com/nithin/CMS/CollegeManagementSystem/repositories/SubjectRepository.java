package com.nithin.CMS.CollegeManagementSystem.repositories;

import com.nithin.CMS.CollegeManagementSystem.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
}
