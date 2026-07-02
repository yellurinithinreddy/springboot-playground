package com.nithin.LMS.LibraryManagementSystem.repositories;

import com.nithin.LMS.LibraryManagementSystem.TestContainerConfiguration;
import com.nithin.LMS.LibraryManagementSystem.entities.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfiguration.class)
class AuthorRepositoryTest {


    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    void setUp(){
        author = Author.builder()
                .authorName("Nithin")
                .build();
    }

    @Test
    void testFindByAuthorName_whenAuthorIsPresent_thenReturnAuthors(){
//        arrange
        Author savedAuthor = authorRepository.save(author);
        List<Author> list = List.of(savedAuthor);
//        act
        List<Author> authors = authorRepository.findByAuthorName(savedAuthor.getAuthorName());
//        assert


        assertThat(authors).isNotEmpty();
        assertThat(authors).isEqualTo(list);

    }

    @Test
    void testFindByAuthorName_whenAuthorIsNotPresent_thenReturnEmptyList(){
//        arrange

//        act
        List<Author> authors = authorRepository.findByAuthorName(author.getAuthorName());
//        assert

        assertThat(authors).isEmpty();
    }



}