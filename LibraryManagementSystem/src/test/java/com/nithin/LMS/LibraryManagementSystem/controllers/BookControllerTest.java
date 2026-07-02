package com.nithin.LMS.LibraryManagementSystem.controllers;

import com.nithin.LMS.LibraryManagementSystem.TestContainerConfiguration;
import com.nithin.LMS.LibraryManagementSystem.dto.BookDTO;
import com.nithin.LMS.LibraryManagementSystem.entities.Book;
import com.nithin.LMS.LibraryManagementSystem.repositories.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
@AutoConfigureWebTestClient
class BookControllerTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Book book;

    private BookDTO bookDTO;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        book = Book.builder()
                .title("book")
                .publishedDate(LocalDate.of(2024,1,1))
                .build();


        bookDTO = modelMapper.map(book,BookDTO.class);

        bookRepository.deleteAll();
    }

    @Test
    void testCreateBook_thenReturnCreatedBook(){
        webTestClient.post()
                .uri("/books")
                .bodyValue(bookDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.title").isEqualTo(book.getTitle())
                .jsonPath("$.publishedDate").isEqualTo(book.getPublishedDate());
    }

    @Test
    void testGetAllBooks_whenBooksArePresent_thenReturnBooks(){
        Book savedBook = bookRepository.save(book);

        EntityExchangeResult<List<BookDTO>> res = webTestClient.get()
                .uri("/books")
                .exchange()
                .expectBodyList(BookDTO.class)
                .returnResult();

        List<BookDTO> books = res.getResponseBody();

        assertThat(books).hasSize(1);
        assertThat(books).isNotEmpty();
        assertThat(books.getFirst().getTitle()).isEqualTo(savedBook.getTitle());
        assertThat(books.getFirst().getPublishedDate()).isEqualTo(savedBook.getPublishedDate());


    }
    @Test
    void testGetAllBooks_whenBooksAreNotPresent_thenReturnEmptyList(){

        EntityExchangeResult<List<BookDTO>> res = webTestClient.get()
                .uri("/books")
                .exchange()
                .expectBodyList(BookDTO.class)
                .returnResult();

        List<BookDTO> books = res.getResponseBody();

        assertThat(books).hasSize(0);
        assertThat(books).isEmpty();


    }

    @Test
    void testGetBookById_whenBookIsPresent_thenReturnBook(){
        Book savedBook = bookRepository.save(book);
        webTestClient.get()
                .uri("/books/{bookId}",savedBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDTO.class)
                .value(book ->{
                    assertThat(book).isNotNull();
                    assertThat(book.getTitle()).isEqualTo(savedBook.getTitle());
                    assertThat(book.getPublishedDate()).isEqualTo(savedBook.getPublishedDate());
                });
    }

    @Test
    void testGetBookById_whenBookIsNotPresent_thenThrowException(){

        webTestClient.get()
                .uri("/books/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateBook_whenBookIsNotPresent_thenThrowException(){

        webTestClient.put()
                .uri("/books/1")
                .bodyValue(bookDTO)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateBook_whenBookIsPresent_thenReturnBook(){
        Book savedBook = bookRepository.save(book);
        bookDTO.setPublishedDate(LocalDate.of(2022,1,1));
        webTestClient.put()
                .uri("/books/{bookId}",savedBook.getId())
                .bodyValue(bookDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDTO.class)
                .value(book -> {
                    assertThat(book).isNotNull();
                    assertThat(book.getTitle()).isEqualTo(bookDTO.getTitle());
                    assertThat(book.getPublishedDate()).isEqualTo(bookDTO.getPublishedDate());
                });
    }

    @Test
    void testUpdateBook_whenAttemptedToChangeTitle_theThrowException(){
        Book savedBook = bookRepository.save(book);
        bookDTO.setTitle("ksdjdfh");
        bookDTO.setPublishedDate(LocalDate.of(2022,1,1));
        webTestClient.put()
                .uri("/books/{bookId}",savedBook.getId())
                .bodyValue(bookDTO)
                .exchange()
                .expectStatus().is5xxServerError();
    }



    @Test
    void testDeleteBook_whenBookIsNotPresent_thenThrowException(){
        webTestClient.delete()
                .uri("/books/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteBook_whenBookIsPresent_thenDeleteBook(){
        Book savedBook = bookRepository.save(book);
        webTestClient.delete()
                .uri("/books/{bookId}",savedBook.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetBooksByTitle_whenBooksArePresent_thenReturnBooks(){
        Book savedBook = bookRepository.save(book);

        webTestClient.get()
                .uri("/books/title/{title}",savedBook.getTitle())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO.class)
                .value(books -> {
                    assertThat(books).isNotEmpty();
                    assertThat(books).hasSize(1);
                    assertThat(books.getFirst().getTitle()).isEqualTo(savedBook.getTitle());
                });
    }

    @Test
    void testGetBooksByTitle_whenBooksAreNotPresent_thenReturnEmptyListOfBooks(){

        webTestClient.get()
                .uri("/books/title/skdjf")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO.class)
                .value(books -> {
                    assertThat(books).isEmpty();
                    assertThat(books).hasSize(0);
                });
    }

    @Test
    void testGetBooksAfterPublishedDate_whenBooksArePresent_thenReturnBooks(){
        Book savedBook = bookRepository.save(book);

        webTestClient.get()
                .uri("/books/publishedDate/{date}",LocalDate.of(2023,12,31))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO.class)
                .value(books -> {
                    assertThat(books).isNotEmpty();
                    assertThat(books).hasSize(1);
                    assertThat(books.getFirst().getTitle()).isEqualTo(savedBook.getTitle());
                });
    }

    @Test
    void testGetBooksAfterPublishedDate_whenBooksAreNotPresent_thenReturnEmptyListOfBooks(){

        webTestClient.get()
                .uri("/books/publishedDate/2020-01-01")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO.class)
                .value(books -> {
                    assertThat(books).isEmpty();
                    assertThat(books).hasSize(0);
                });
    }




}