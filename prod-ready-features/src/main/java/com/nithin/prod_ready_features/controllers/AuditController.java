package com.nithin.prod_ready_features.controllers;

import com.nithin.prod_ready_features.entities.PostEntity;
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
public class AuditController {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @GetMapping("/posts/{postId}")
    public List<PostEntity> getAllRevisionsForPost(@PathVariable Long postId){
        AuditReader auditReader = AuditReaderFactory.get(entityManagerFactory.createEntityManager());

        List<Number> revisions = auditReader.getRevisions(PostEntity.class,postId);

        return revisions
                .stream()
                .map(revisionNumber -> auditReader.find(PostEntity.class,postId,revisionNumber))
                .toList();
    }
}
