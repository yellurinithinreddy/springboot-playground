package com.nithin.LMS.LibraryManagementSystem.controllers;

import com.nithin.LMS.LibraryManagementSystem.dto.AuthorDTO;
import com.nithin.LMS.LibraryManagementSystem.dto.BookDTO;
import com.nithin.LMS.LibraryManagementSystem.services.AuthorService;
import com.nithin.LMS.LibraryManagementSystem.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO){
        return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long bookId){
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable Long bookId,@RequestBody BookDTO bookDTO){
        return ResponseEntity.ok(bookService.updateBook(bookId,bookDTO));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Boolean> deleteBookById(@PathVariable Long bookId){
        return ResponseEntity.ok(bookService.deleteBook(bookId));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDTO>> getBooksByTitle(@PathVariable String title){
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("publishedDate/{date}")
    public ResponseEntity<List<BookDTO>> getBooksAfterPublishedDate(@PathVariable LocalDate date){
        return ResponseEntity.ok(bookService.getBooksAfterPublishedDate(date));
    }
}
