package com.nithin.LMS.LibraryManagementSystem.controllers;

import com.nithin.LMS.LibraryManagementSystem.dto.AuthorDTO;
import com.nithin.LMS.LibraryManagementSystem.dto.BookDTO;
import com.nithin.LMS.LibraryManagementSystem.entities.Book;
import com.nithin.LMS.LibraryManagementSystem.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody AuthorDTO authorDTO){
        return new ResponseEntity<>(authorService.createAuthor(authorDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors(){
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable(name = "authorId") Long id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> updateAuthorById(@PathVariable(name = "authorId") Long id,@RequestBody AuthorDTO authorDTO){
        return ResponseEntity.ok(authorService.updateAuthor(id,authorDTO));
    }

    @DeleteMapping(path = "/{authorId}")
    public ResponseEntity<Boolean> deleteAuthorById(@PathVariable Long authorId){
        return ResponseEntity.ok(authorService.deleteAuthor(authorId));
    }

    @GetMapping("/name/{authorName}")
    public ResponseEntity<List<AuthorDTO>> getAuthorsByName(@PathVariable String authorName){
        return ResponseEntity.ok(authorService.getAuthorsByName(authorName));
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<BookDTO>> getBooksWrittenByAuthor(@PathVariable Long authorId){
        return ResponseEntity.ok(authorService.getBooksWrittenByAuthor(authorId));
    }

    @PostMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<AuthorDTO> assignBookToAuthor(@PathVariable Long authorId,@PathVariable Long bookId){
        return ResponseEntity.ok(authorService.assignBooktoAuthor(authorId,bookId));
    }
}
