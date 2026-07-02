package com.nithin.LMS.LibraryManagementSystem.controllers;

import com.nithin.LMS.LibraryManagementSystem.TestContainerConfiguration;
import com.nithin.LMS.LibraryManagementSystem.dto.AuthorDTO;
import com.nithin.LMS.LibraryManagementSystem.dto.BookDTO;
import com.nithin.LMS.LibraryManagementSystem.entities.Author;
import com.nithin.LMS.LibraryManagementSystem.entities.Book;
import com.nithin.LMS.LibraryManagementSystem.repositories.AuthorRepository;
import com.nithin.LMS.LibraryManagementSystem.repositories.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(TestContainerConfiguration.class)
class AuthorControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private Author author;

    private AuthorDTO authorDTO;

    private Book book;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        author = Author.builder()
                .authorName("Nithin")
                .authorAge(24)
                .build();

        book = Book.builder()
                .title("Book")
                .publishedDate(LocalDate.of(2024,1,1))
                .author(author)
                .build();

        authorDTO = modelMapper.map(author,AuthorDTO.class);
        authorRepository.deleteAll();
    }

    @Test
    void testAddAuthor_thenReturnSavedAuthor(){
        webTestClient.post()
                .uri("/authors")
                .bodyValue(authorDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.authorName").isEqualTo(author.getAuthorName())
                .jsonPath("$.authorAge").isEqualTo(author.getAuthorAge());
    }

    @Test
    void testGetAllAuthors_whenAuthorsArePresent_thenReturnAuthors(){
        authorRepository.save(author);
//
//        webTestClient.get()
//                .uri("/authors")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$.length()").isEqualTo(1)
//                .jsonPath("$[0].authorName").isEqualTo(author.getAuthorName())
//                .jsonPath("$[0].authorAge").isEqualTo(author.getAuthorAge());

//        webTestClient.get()
//                .uri("/authors")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(AuthorDTO.class)
//                .value(authors -> {
//                    assertThat(authors).hasSize(1);
//                    assertThat(authors.get(0).getAuthorName()).isEqualTo(authorDTO.getAuthorName());
//                    assertThat(authors.get(0).getAuthorAge()).isEqualTo(authorDTO.getAuthorAge());
//                });


        EntityExchangeResult<List<AuthorDTO>> res = webTestClient.get()
                .uri("/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDTO.class)
                .returnResult();

        List<AuthorDTO> authors = res.getResponseBody();
        assertThat(authors).hasSize(1);
        assertThat(authors.getFirst().getAuthorAge()).isEqualTo(author.getAuthorAge());
    }

    @Test
    void testGetAllAuthors_whenAuthorsAreNotPresent_thenReturnEmptyList(){

        webTestClient.get()
                .uri("/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDTO.class)
                .value(authors ->{
                    assertThat(authors).isEmpty();
                    assertThat(authors).hasSize(0);
                });

        EntityExchangeResult<List<AuthorDTO>> res = webTestClient.get()
                .uri("/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDTO.class)
                .returnResult();

        List<AuthorDTO> authors = res.getResponseBody();
        assertThat(authors).isEmpty();
        assertThat(authors).hasSize(0);
    }

    @Test
    void testGetAuthorById_whenAuthorIsNotPresent_thenThrowException(){

        webTestClient.get()
                .uri("/authors/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetAuthorById_whenAuthorIsPresent_thenReturnAuthor(){
        Author savedAuthor = authorRepository.save(author);
        webTestClient.get()
                .uri("/authors/{authorId}",savedAuthor.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.authorAge").isEqualTo(savedAuthor.getAuthorAge())
                .jsonPath("$.authorName").isEqualTo(savedAuthor.getAuthorName());


        webTestClient.get()
                .uri("/authors/{authorId}",savedAuthor.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorDTO.class)
                .value(author -> {
                    assertThat(author.getAuthorName()).isEqualTo(savedAuthor.getAuthorName());
                    assertThat(author.getAuthorAge()).isEqualTo(savedAuthor.getAuthorAge());
                });
    }

    @Test
    void testGetBooksWrittenByAuthor_whenBooksArePresent_thenReturnBooks(){
        Author savedAuthor = authorRepository.save(author);
        Book savedbook = bookRepository.save(book);

        book.setAuthor(savedAuthor);
        savedAuthor.getBooks().add(savedbook);

        authorRepository.save(author);

        webTestClient.get()
                .uri("/authors/{authorId}/books",savedAuthor.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO.class)
                .value(books ->{
                    assertThat(books).isNotEmpty();
                    assertThat(books).hasSize(1);
                    assertThat(books.get(0).getTitle()).isEqualTo(savedbook.getTitle());
                    assertThat(books.get(0).getPublishedDate()).isEqualTo(savedbook.getPublishedDate());
                });

//        webTestClient.get()
//                .uri("/authors/{authorId}/books",savedAuthor.getId())
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(String.class)
//                .consumeWith(res ->{
//                    System.out.println(res.getStatus());
//                    System.out.println(res.getResponseBody());
//                });

    }

    @Test
    void testGetAuthorsByName_whenAuthorsArePresent_thenReturnAuthors(){
        Author savedAuthor = authorRepository.save(author);


        webTestClient.get()
                .uri("/authors/name/{authorName}",savedAuthor.getAuthorName())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDTO.class)
                .value(authors ->{
                    assertThat(authors).hasSize(1);
                    assertThat(authors.get(0).getAuthorAge()).isEqualTo(savedAuthor.getAuthorAge());
                    assertThat(authors.get(0).getAuthorName()).isEqualTo(savedAuthor.getAuthorName());
                });

    }

    @Test
    void testGetAuthorsByName_whenAuthorsAreNotPresent_thenReturnEmptyList(){

        webTestClient.get()
                .uri("/authors/name/Nithin")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDTO.class)
                .value(authors ->{
                    assertThat(authors).isEmpty();
                });
    }

    @Test
    void testGetBooksWrittenByAuthor_whenBooksAreNotPresent_thenReturnEmptyListOfBooks(){
        Author savedAuthor = authorRepository.save(author);

        webTestClient.get()
                .uri("/authors/{authorId}/books",savedAuthor.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDTO.class)
                .value(books ->{
                    assertThat(books).isEmpty();
                    assertThat(books).hasSize(0);
                });

    }

    @Test
    void testAssignBookToAuthor_whenAuthorIsPresentAndBookIsNotPresent_thenThrowException(){
        Author savedAuthor = authorRepository.save(author);

        webTestClient.post()
                .uri("/authors/{authorId}/books/1",savedAuthor.getId())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testAssignBookToAuthor_whenAuthorIsNotPresentAndBookIsNotOrIsPresent_thenThrowException(){

        webTestClient.post()
                .uri("/authors/1/books/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testAssignBookToAuthor_whenAuthorIsPresentAndBookIsPresent_thenReturnAuthor(){
        Author savedAuthor = authorRepository.save(author);
        Book savedBook = bookRepository.save(book);

        webTestClient.post()
                .uri("/authors/{authorId}/books/{bookId}",savedAuthor.getId(),savedBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorDTO.class)
                .value(author ->{
                    assertThat(author).isNotNull();
                    assertThat(author.getBooks().get(0).getTitle()).isEqualTo(savedBook.getTitle());
                    assertThat(author.getBooks().get(0).getPublishedDate()).isEqualTo(savedBook.getPublishedDate());
                });
    }

    @Test
    void testUpdateAuthorById_whenAuthorIsNotPresent_thenThrowException(){
        webTestClient.put()
                .uri("/authors/1")
                .bodyValue(authorDTO)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateAuthorById_whenAttemptedToChangeAuthorName_thenThrowException(){
        Author savedAuthor = authorRepository.save(author);
        authorDTO.setAuthorName("Lobian");
        authorDTO.setAuthorAge(64);

        webTestClient.put()
                .uri("/authors/{authorId}",savedAuthor.getId())
                .bodyValue(authorDTO)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testUpdateAuthorById_whenAuthorIsPresent_thenReturnAuthor(){
        Author savedAuthor = authorRepository.save(author);
        authorDTO.setAuthorAge(64);

        webTestClient.put()
                .uri("/authors/{authorId}",savedAuthor.getId())
                .bodyValue(authorDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.authorAge").isEqualTo(authorDTO.getAuthorAge())
                .jsonPath("$.authorName").isEqualTo(authorDTO.getAuthorName());

//        webTestClient.put()
//                .uri("/authors/{authorId}",savedAuthor.getId())
//                .bodyValue(authorDTO)
//                .exchange()
//                .expectBody(String.class)
//                .consumeWith(res ->{
//                    System.out.println(res.getStatus());
//                    System.out.println(res.getResponseBody());
//                });
    }

    @Test
    void testDeleteAuthorById_whenAuthorIsPresent_thenDeleteAuthor(){
        Author savedAuthor = authorRepository.save(author);

        webTestClient.delete()
                .uri("authors/{authorId}",savedAuthor.getId())
                .exchange()
                .expectStatus().isOk();

    }
    @Test
    void testDeleteAuthorById_whenAuthorIsNotPresent_thenThrowException(){

        webTestClient.delete()
                .uri("authors/1")
                .exchange()
                .expectStatus().isNotFound();

    }


}