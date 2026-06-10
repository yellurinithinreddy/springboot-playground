package com.nithin.LMS.LibraryManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nithin.LMS.LibraryManagementSystem.entities.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    private Long id;

    private String authorName;

    private List<Book> books;
}
