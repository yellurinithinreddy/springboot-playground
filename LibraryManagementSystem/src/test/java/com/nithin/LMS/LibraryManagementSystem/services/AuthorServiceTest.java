package com.nithin.LMS.LibraryManagementSystem.services;

import com.nithin.LMS.LibraryManagementSystem.dto.AuthorDTO;
import com.nithin.LMS.LibraryManagementSystem.dto.BookDTO;
import com.nithin.LMS.LibraryManagementSystem.entities.Author;
import com.nithin.LMS.LibraryManagementSystem.entities.Book;
import com.nithin.LMS.LibraryManagementSystem.exceptions.ResourceNotFoundException;
import com.nithin.LMS.LibraryManagementSystem.repositories.AuthorRepository;
import com.nithin.LMS.LibraryManagementSystem.repositories.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    private AuthorDTO authorDTO;

    private Book book;

    @BeforeEach
    void setUp(){
        author = Author.builder()
                .id(1L)
                .authorName("Nithin")
                .authorAge(43)
                .build();

        book = Book.builder()
                .id(1L)
                .title("book")
                .publishedDate(LocalDate.of(2024,1,1))
                .build();

        authorDTO = modelMapper.map(author,AuthorDTO.class);
    }

    @Test
    void testCreateAuthor_thenReturnSavedAuthor(){
//        arrange
        when(authorRepository.save(any())).thenReturn(author);
//        act

        AuthorDTO result = authorService.createAuthor(authorDTO);
//        assert

        assertThat(result.getAuthorName()).isEqualTo(author.getAuthorName());
        verify(authorRepository).save(any());
    }

    @Test
    void testGetAllAuthors_whenAuthorsArePresent_thenReturnAllAuthors(){
//        arrange
        when(authorRepository.findAll()).thenReturn(List.of(author));
//        act

        List<AuthorDTO> authors = authorService.getAllAuthors();
//        assert

        assertThat(authors).isNotEmpty();
        verify(authorRepository,times(1)).findAll();
    }

    @Test
    void testGetAllAuthors_whenAuthorsAreNotPresent_thenReturnEmptyList(){
//        arrange
        when(authorRepository.findAll()).thenReturn(List.of());
//        act

        List<AuthorDTO> authors = authorService.getAllAuthors();
//        assert

        assertThat(authors).isEmpty();
        verify(authorRepository,times(1)).findAll();
    }

    @Test
    void testGetAuthorById_whenAuthorIsNotPresent_thenThrowException(){
//        arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act and assert

        assertThatThrownBy(() -> authorService.getAuthorById(author.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Author not found with id: "+author.getId());

        verify(authorRepository).findById(anyLong());
    }

    @Test
    void testGetAuthorById_whenAuthorIsPresent_thenReturnAuthor(){
//        arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
//        act
        AuthorDTO resultAuthor = authorService.getAuthorById(author.getId());
//        assert

        assertThat(resultAuthor).isEqualTo(authorDTO);
        verify(authorRepository).findById(anyLong());

    }

    @Test
    void testUpdateAuthor_whenAuthorIsNotFound_thenThrowException(){
//        arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act and assert

        assertThatThrownBy(() -> authorService.updateAuthor(author.getId(), authorDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Author not found with id: "+author.getId());
        verify(authorRepository).findById(anyLong());
        verify(authorRepository,never()).save(any());
    }

    @Test
    void testUpdateAuthor_whenAttemptedToUpdateAuthorName_thenThrowException(){
//        arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        authorDTO.setAuthorName("Pope");
        authorDTO.setAuthorAge(40);
//        act and assert

        assertThatThrownBy(() -> authorService.updateAuthor(author.getId(), authorDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Author name cannot be updated");

        verify(authorRepository).findById(anyLong());
        verify(authorRepository,never()).save(any());
    }

    @Test
    void testUpdateAuthor_whenAuthorIsPresent_thenUpdateAuthorAndReturnUpdatedAuthor(){
//        arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        authorDTO.setAuthorAge(24);
        Author author = modelMapper.map(authorDTO,Author.class);
        when(authorRepository.save(any())).thenReturn(author);
//        act

        AuthorDTO resultAuthor = authorService.updateAuthor(author.getId(), authorDTO);
//        assert

        assertThat(resultAuthor).isNotNull();
        assertThat(resultAuthor).isEqualTo(authorDTO);
        verify(authorRepository).findById(anyLong());
        verify(authorRepository).save(any());
    }

    @Test
    void testDeleteAuthor_whenAuthorIsNotPresent_thenThrowException(){
//        arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act and assert

        assertThatThrownBy(() -> authorService.deleteAuthor(author.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Author not found with id: "+author.getId());

        verify(authorRepository).findById(anyLong());
        verify(authorRepository,never()).deleteById(anyLong());
    }

    @Test
    void testDeleteAuthor_whenAuthorIsPresent_thenDeleteAuthor(){
//        arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
//        act and assert

        assertThatCode(() -> authorService.deleteAuthor(author.getId()))
                .doesNotThrowAnyException();
        verify(authorRepository).findById(anyLong());
        verify(authorRepository).deleteById(anyLong());

    }

    @Test
    void testGetAuthorsByName_whenAuthorsArePresent_thenReturnAuthors(){
//        arrange
        when(authorRepository.findByAuthorName(any())).thenReturn(List.of(author));
//        act

        List<AuthorDTO> authors = authorService.getAuthorsByName(author.getAuthorName());
//        assert

        assertThat(authors).isNotEmpty();
        assertThat(authors.get(0).getAuthorName()).isEqualTo(author.getAuthorName());
        verify(authorRepository).findByAuthorName(any());
    }

    @Test
    void testGetAuthorsByName_whenAuthorsAreNotPresent_thenReturnEmptyList(){
//        arrange
        when(authorRepository.findByAuthorName(any())).thenReturn(List.of());
//        act

        List<AuthorDTO> authors = authorService.getAuthorsByName(author.getAuthorName());
//        assert

        assertThat(authors).isEmpty();
        verify(authorRepository).findByAuthorName(any());
    }


    @Test
    void testGetBooksWrittenByAuthor_whenAuthorIsNotPresent_theThrowException(){
        //        arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act and assert

        assertThatThrownBy(() -> authorService.getBooksWrittenByAuthor(author.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Author not found with id: "+author.getId());

        verify(authorRepository).findById(anyLong());
    }

    @Test
    void testGetBooksWrittenByAuthor_whenAuthorIsPresent_thenReturnBooks(){
        //        arrange
        List<Book> bs = List.of(book);
        author.setBooks(bs);
        book.setAuthor(author);
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));

//        act and assert

        List<BookDTO> books = authorService.getBooksWrittenByAuthor(author.getId());
        assertThat(books.get(0).getTitle()).isEqualTo(author.getBooks().get(0).getTitle());
        assertThat(books.get(0).getPublishedDate()).isEqualTo(author.getBooks().get(0).getPublishedDate());

        verify(authorRepository).findById(anyLong());
    }

    @Test
    void testAssignBookToAuthor_whenAuthorIsNotPresentAndBookIsNotPresent_thenThrowException(){
//        arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act and assert

        assertThatThrownBy(() -> authorService.assignBooktoAuthor(author.getId(), book.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Author not found with Id: "+author.getId());

        verify(authorRepository).findById(anyLong());
        verify(bookRepository,never()).findById(anyLong());


    }

    @Test
    void testAssignBookToAuthor_whenAuthorIsPresentAndBookIsNotPresent_thenThrowException(){
//        arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act and assert

        assertThatThrownBy(() -> authorService.assignBooktoAuthor(author.getId(), book.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book not found with Id: "+book.getId());

        verify(authorRepository).findById(anyLong());
        verify(bookRepository).findById(anyLong());


    }

    @Test
    void testAssignBookToAuthor_whenAuthorIsPresentAndBookIsPresent_thenAssignBook(){
//        arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
//        act

        AuthorDTO authorDTO1 = authorService.assignBooktoAuthor(author.getId(), book.getId());
//        assert

        assertThat(authorDTO1.getBooks().get(0).getTitle()).isEqualTo(book.getTitle());
        assertThat(authorDTO1.getAuthorName()).isEqualTo(book.getAuthor().getAuthorName());
        assertThat(authorDTO1.getAuthorAge()).isEqualTo(book.getAuthor().getAuthorAge());
        verify(authorRepository).findById(anyLong());
        verify(bookRepository).findById(anyLong());
    }


}