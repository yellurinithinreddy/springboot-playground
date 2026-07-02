package com.nithin.LMS.LibraryManagementSystem.configs;

import com.nithin.LMS.LibraryManagementSystem.dto.AuthorDTO;
import com.nithin.LMS.LibraryManagementSystem.entities.Author;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(AuthorDTO.class, Author.class)
                .addMappings(m -> m.skip(Author::setBooks));

        return mapper;
    }
}
