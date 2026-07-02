package com.nithin.LMS.LibraryManagementSystem.repositories;

import com.nithin.LMS.LibraryManagementSystem.TestContainerConfiguration;
import com.nithin.LMS.LibraryManagementSystem.entities.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfiguration.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp(){
        book = Book.builder()
                .title("book")
                .publishedDate(LocalDate.of(2024,1,1))
                .build();
    }

    @Test
    void testFindByTitle_whenBooksArePresent_thenReturnListOfBooks(){
//        arrange
        Book b1 = bookRepository.save(book);
        List<Book> list = List.of(b1);
//        act
        List<Book> books = bookRepository.findByTitle(book.getTitle());
//        assert

        assertThat(books).isNotEmpty();
        assertThat(books).isEqualTo(list);
    }

    @Test
    void testFindByTitle_whenBooksAreNotPresent_thenReturnEmptyList(){
//        act
        List<Book> books = bookRepository.findByTitle(book.getTitle());
//        assert

        assertThat(books).isEmpty();
    }


    @Test
    void testFindByPublishedDateAfter_whenBooksArePresent_thenReturnListOfBooks(){
//        arrange
        Book b1 = bookRepository.save(book);
        List<Book> list = List.of(b1);
//        act

        List<Book> books = bookRepository.findByPublishedDateAfter(LocalDate.of(2023,12,29));
//        assert

        assertThat(books).isNotEmpty();
        assertThat(books).isEqualTo(list);
    }

    @Test
    void testFindByPublishedDateAfter_whenBooksAreNotPresent_thenReturnEmptyList(){
//        act

        List<Book> books = bookRepository.findByPublishedDateAfter(LocalDate.of(2023,12,29));
//        assert

        assertThat(books).isEmpty();
    }


}