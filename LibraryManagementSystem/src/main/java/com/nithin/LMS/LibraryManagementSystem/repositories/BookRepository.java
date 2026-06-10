package com.nithin.LMS.LibraryManagementSystem.repositories;

import com.nithin.LMS.LibraryManagementSystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findByTitle(String title);

    List<Book> findByPublishedDateAfter(LocalDate date);
}
