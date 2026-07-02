package com.nithin.LMS.LibraryManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nithin.LMS.LibraryManagementSystem.entities.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorDTO {

    private Long id;

    private String authorName;

    private int authorAge;

    private List<BookDTO> books = new ArrayList<>();

}
