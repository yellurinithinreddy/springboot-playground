package com.nithin.CMS.CollegeManagementSystem.controllers;

import com.nithin.CMS.CollegeManagementSystem.entities.Professor;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
public class ProfessorAuditController {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @GetMapping("/prof/{id}")
    List<Professor> getAllProfessorRevisions(@PathVariable Long id){
        AuditReader auditReader = AuditReaderFactory.get(entityManagerFactory.createEntityManager());

        List<Number> profRevisions = auditReader.getRevisions(Professor.class,id);
        return profRevisions.stream().map(revisionNum -> auditReader.find(Professor.class,id,revisionNum)).toList();

    }
}
