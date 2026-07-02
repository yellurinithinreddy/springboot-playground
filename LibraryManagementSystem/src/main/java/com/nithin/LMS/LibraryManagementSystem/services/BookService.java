package com.nithin.LMS.LibraryManagementSystem.services;


import com.nithin.LMS.LibraryManagementSystem.dto.BookDTO;

import com.nithin.LMS.LibraryManagementSystem.entities.Book;
import com.nithin.LMS.LibraryManagementSystem.exceptions.ResourceNotFoundException;
import com.nithin.LMS.LibraryManagementSystem.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO,Book.class);
        return modelMapper.map(bookRepository.save(book),BookDTO.class);
    }

    @Transactional
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.
                stream()
                .map(book -> modelMapper.map(book,BookDTO.class))
                .toList();
    }

    @Transactional
    public BookDTO getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->{
            throw new ResourceNotFoundException("Book not found with Id: "+bookId);
        });

        return modelMapper.map(book,BookDTO.class);
    }

    @Transactional
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
        Book book = bookRepository.findById(bookId)
                        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: "+bookId));

        if(!book.getTitle().equals(bookDTO.getTitle())){
            throw new RuntimeException("book title cannot be changed");
        }
        bookDTO.setId(bookId);
        modelMapper.map(bookDTO,book);
        return modelMapper.map(bookRepository.save(book),BookDTO.class);
    }

    @Transactional
    public Boolean deleteBook(Long bookId) {
        bookRepository.findById(bookId).orElseThrow(() ->{
            throw new ResourceNotFoundException("Book not found with id: "+bookId);
        });

        bookRepository.deleteById(bookId);
        return true;
    }

    @Transactional
    public List<BookDTO> getBooksByTitle(String title) {
        List<Book> books= bookRepository.findByTitle(title);
        return books.
                stream()
                .map(book -> modelMapper.map(book,BookDTO.class))
                .toList();
    }

    public List<BookDTO> getBooksAfterPublishedDate(LocalDate date) {
        List<Book> books = bookRepository.findByPublishedDateAfter(date);
        return books.
                stream()
                .map(book -> modelMapper.map(book,BookDTO.class))
                .toList();
    }
}
