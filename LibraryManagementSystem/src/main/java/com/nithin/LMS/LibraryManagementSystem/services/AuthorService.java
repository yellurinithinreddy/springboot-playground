package com.nithin.LMS.LibraryManagementSystem.services;

import com.nithin.LMS.LibraryManagementSystem.dto.AuthorDTO;
import com.nithin.LMS.LibraryManagementSystem.dto.BookDTO;
import com.nithin.LMS.LibraryManagementSystem.entities.Author;
import com.nithin.LMS.LibraryManagementSystem.entities.Book;
import com.nithin.LMS.LibraryManagementSystem.exceptions.ResourceNotFoundException;
import com.nithin.LMS.LibraryManagementSystem.repositories.AuthorRepository;
import com.nithin.LMS.LibraryManagementSystem.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    private final BookRepository bookRepository;

    private final Logger log = LoggerFactory.getLogger(AuthorService.class);

    @Transactional
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        log.info("Started creating Author in the author entity with {}",authorDTO);
        Author author = modelMapper.map(authorDTO,Author.class);
        return modelMapper.map(authorRepository.save(author),AuthorDTO.class);
    }

    @Transactional
    public List<AuthorDTO> getAllAuthors() {
        log.info("started retrieving authors in getAllAuthors");
        List<Author> authors = authorRepository.findAll();

        log.info("Succesfully retrieved authors {}",authors);
        return authors.stream()
                .map(author -> modelMapper.map(author,AuthorDTO.class))
                .toList();
    }

    @Transactional
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() ->{
            throw new ResourceNotFoundException("Author not found with id: "+id);
        });

        return modelMapper.map(author,AuthorDTO.class);
    }

    @Transactional
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: "+id));
        if(!author.getAuthorName().equals(authorDTO.getAuthorName())){
            throw new RuntimeException("Author name cannot be updated");
        }
        authorDTO.setId(id);
        modelMapper.map(authorDTO,author);
        return modelMapper.map(authorRepository.save(author),AuthorDTO.class);
    }

    @Transactional
    public Boolean deleteAuthor(Long authorId) {
        authorRepository.findById(authorId).orElseThrow(() ->{
            throw new ResourceNotFoundException("Author not found with id: "+authorId);
        });
        authorRepository.deleteById(authorId);
        return true;
    }

    @Transactional
    public List<AuthorDTO> getAuthorsByName(String authorName) {
        List<Author> authors = authorRepository.findByAuthorName(authorName);
        return authors.stream()
                .map(author -> modelMapper.map(author,AuthorDTO.class))
                .toList();
    }


    @Transactional
    public List<BookDTO> getBooksWrittenByAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() ->{
            throw new ResourceNotFoundException("Author not found with id: "+authorId);
        });

        return author.getBooks().
                stream()
                .map(book -> modelMapper.map(book,BookDTO.class))
                .toList();

    }

    @Transactional
    public AuthorDTO assignBooktoAuthor(Long authorId,Long bookId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found with Id: " + authorId));

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with Id: " + bookId));

        author.getBooks().add(book);
        book.setAuthor(author);
        return modelMapper.map(author,AuthorDTO.class);
    }
}
