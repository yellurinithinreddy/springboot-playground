package com.nithin.LMS.LibraryManagementSystem.dto;


import com.nithin.LMS.LibraryManagementSystem.entities.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;

    private String title;

    private LocalDate publishedDate;

    private Author author;
}
