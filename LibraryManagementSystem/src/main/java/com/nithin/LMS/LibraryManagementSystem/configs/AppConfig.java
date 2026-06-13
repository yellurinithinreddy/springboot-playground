package com.nithin.LMS.LibraryManagementSystem.configs;

import com.nithin.LMS.LibraryManagementSystem.auth.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AppConfig {

    @Bean
    AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }
}
