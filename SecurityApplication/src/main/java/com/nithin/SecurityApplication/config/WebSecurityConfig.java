package com.nithin.SecurityApplication.config;

import com.nithin.SecurityApplication.enums.Role;
import com.nithin.SecurityApplication.filters.JwtAuthFilter;
import com.nithin.SecurityApplication.filters.LoggingFilter;
import com.nithin.SecurityApplication.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.nithin.SecurityApplication.enums.Permission.*;
import static com.nithin.SecurityApplication.enums.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final LoggingFilter loggingFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final String[] publicRoutes = {
            "/auth/**","/home.html"
    };
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers("/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyRole(CREATOR.name(),ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyAuthority(POST_CREATE.name())
                        .requestMatchers(HttpMethod.GET,"/posts/**").hasAnyAuthority(POST_VIEW.name())
                        .requestMatchers(HttpMethod.PUT,"/posts/**").hasAnyAuthority(POST_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE,"/posts/**").hasAnyAuthority(POST_DELETE.name())
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loggingFilter, JwtAuthFilter.class)
                .oauth2Login(oauthConfig ->
                        oauthConfig.failureUrl("/login?error=true")
                                .successHandler(oAuth2SuccessHandler)
                );
//                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }


//    @Bean
//    UserDetailsService inMemoryUserDetailsManager(){
//        UserDetails user = User.builder()
//                .username("monu")
//                .password(passwordEncoder().encode("monu1234"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("nithin")
//                .password(passwordEncoder().encode("nithin1234"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user,admin);
//    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }


}
