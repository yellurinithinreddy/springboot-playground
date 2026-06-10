package com.nithin.LMS.LibraryManagementSystem.repositories;

import com.nithin.LMS.LibraryManagementSystem.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    List<Author> findByAuthorName(String authorName);
}
