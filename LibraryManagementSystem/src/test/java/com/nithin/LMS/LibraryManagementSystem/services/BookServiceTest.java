package com.nithin.LMS.LibraryManagementSystem.services;

import com.nithin.LMS.LibraryManagementSystem.dto.BookDTO;
import com.nithin.LMS.LibraryManagementSystem.entities.Book;
import com.nithin.LMS.LibraryManagementSystem.exceptions.ResourceNotFoundException;
import com.nithin.LMS.LibraryManagementSystem.repositories.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;

    private BookDTO bookDTO;

    @BeforeEach
    void setUp(){
        book = Book.builder()
                .id(1L)
                .title("book")
                .publishedDate(LocalDate.of(2024,1,1))
                .build();

        bookDTO = modelMapper.map(book,BookDTO.class);
    }


    @Test
    void testCreateBook_thenSaveBookAndReturnBook(){
//        arrange
        when(bookRepository.save(any())).thenReturn(book);
//        act

        BookDTO bookDTO1 = bookService.createBook(bookDTO);
//        assert

        assertThat(bookDTO1).isNotNull();
        assertThat(bookDTO1).isEqualTo(bookDTO);
        verify(bookRepository).save(any());
    }

    @Test
    void testGetAllBooks_whenBooksArePresent_thenReturnBooks(){
//        arrange
        when(bookRepository.findAll()).thenReturn(List.of(book));
//        act

        List<BookDTO> books = bookService.getAllBooks();
//        assert

        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo(bookDTO.getTitle());
        verify(bookRepository).findAll();
    }

    @Test
    void testGetAllBooks_whenBooksAreNotPresent_thenReturnBooks(){
//        arrange
        when(bookRepository.findAll()).thenReturn(List.of());
//        act

        List<BookDTO> books = bookService.getAllBooks();
//        assert

        assertThat(books).isEmpty();
        verify(bookRepository).findAll();
    }

    @Test
    void testGetBookById_whenBookIsPresent_thenReturnBook(){
//        arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
//        act

        BookDTO b = bookService.getBookById(book.getId());
//        assert

        assertThat(b).isNotNull();
        assertThat(b).isEqualTo(bookDTO);
        verify(bookRepository).findById(anyLong());
    }

    @Test
    void testGetBookById_whenBookIsNotPresent_thenThrowException(){
//        arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act and assert

        assertThatThrownBy(() -> bookService.getBookById(book.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book not found with Id: "+book.getId());

        verify(bookRepository).findById(anyLong());
    }

    @Test
    void testUpdateBook_whenBookIsNotPresent_thenThrowException(){
//        arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act and assert

        assertThatThrownBy(() -> bookService.updateBook(book.getId(), bookDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book not found with id: "+book.getId());

        verify(bookRepository).findById(anyLong());
        verify(bookRepository,never()).save(any());
    }

    @Test
    void testUpdateBook_whenAttemptedToChangeTitle_thenThrowException(){
//        arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        bookDTO.setTitle("others");
        bookDTO.setPublishedDate(LocalDate.of(2023,3,3));
//        act and assert

        assertThatThrownBy(() -> bookService.updateBook(book.getId(),bookDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("book title cannot be changed");

        verify(bookRepository).findById(anyLong());
        verify(bookRepository,never()).save(any());

    }

    @Test
    void testUpdateBook_whenBookIsPresent_thenReturnUpdatedBook(){
//        arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        bookDTO.setPublishedDate(LocalDate.of(2023,1,1));
        Book b1 = modelMapper.map(bookDTO,Book.class);
        when(bookRepository.save(any())).thenReturn(b1);
//        act

        BookDTO resultBook = bookService.updateBook(bookDTO.getId(), bookDTO);
//        assert

        assertThat(resultBook).isNotNull();
        assertThat(resultBook).isEqualTo(bookDTO);

        verify(bookRepository).findById(anyLong());
        verify(bookRepository).save(any());
    }

    @Test
    void testDeleteBook_whenBookIsNotPresent_thenThrowException(){
//        arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act and assert

        assertThatThrownBy(() -> bookService.deleteBook(book.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book not found with id: "+book.getId());

        verify(bookRepository).findById(anyLong());
        verify(bookRepository,never()).deleteById(anyLong());
    }

    @Test
    void testDeleteBook_whenBookIsPresent_thenDeleteBook(){
//        arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
//        act and assert

        assertThatCode(() -> bookService.deleteBook(book.getId()))
                .doesNotThrowAnyException();

        verify(bookRepository).findById(anyLong());
        verify(bookRepository).deleteById(anyLong());
    }

    @Test
    void testGetBooksByTitle_whenBooksArePresent_thenReturnBooks(){
//        arrange
        when(bookRepository.findByTitle(anyString())).thenReturn(List.of(book));
//        act
        List<BookDTO> books = bookService.getBooksByTitle(book.getTitle());
//        assert

        assertThat(books).isNotEmpty();
        assertThat(books.get(0)).isEqualTo(bookDTO);
        verify(bookRepository).findByTitle(anyString());
    }

    @Test
    void testGetBooksByTitle_whenBooksAreNotPresent_thenReturnEmptyList(){
//        arrange
        when(bookRepository.findByTitle(anyString())).thenReturn(List.of());
//        act
        List<BookDTO> books = bookService.getBooksByTitle(book.getTitle());
//        assert

        assertThat(books).isEmpty();
        verify(bookRepository).findByTitle(any());
    }

    @Test
    void testGetBooksAfterPublishedDate_whenBooksArePresent_thenReturnBooks(){
//        arrange
        when(bookRepository.findByPublishedDateAfter(any())).thenReturn(List.of(book));
//        act

        List<BookDTO> books = bookService.getBooksAfterPublishedDate(book.getPublishedDate());
//        assert

        assertThat(books).isNotEmpty();
        assertThat(books.get(0)).isEqualTo(bookDTO);

        verify(bookRepository).findByPublishedDateAfter(any());
    }

    @Test
    void testGetBooksAfterPublishedDate_whenBooksAreNotPresent_thenReturnEmptyList(){
//        arrange
        when(bookRepository.findByPublishedDateAfter(any())).thenReturn(List.of());
//        act

        List<BookDTO> books = bookService.getBooksAfterPublishedDate(book.getPublishedDate());
//        assert

        assertThat(books).isEmpty();

        verify(bookRepository).findByPublishedDateAfter(any());
    }

}