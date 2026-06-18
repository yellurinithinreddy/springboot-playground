package com.nithin.SecurityApplication.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Method: {}", request.getMethod());
        log.info("URI: {}", request.getRequestURI());
        log.info("Query String: {}", request.getQueryString());
        log.info("Remote Address: {}", request.getRemoteAddr());
        log.info("Content Type: {}", request.getContentType());
        log.info("CharacterEncoding: {}",request.getCharacterEncoding());
        log.info("Server port: {}", request.getServerPort());
        log.info("Cookies: {}", (Object) request.getCookies());
        log.info("Session Id: {}",request.getSession().getId());
        Enumeration<String>  headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String header = headerNames.nextElement();
            log.info("{} : {}", header, request.getHeader(header));
        }

        log.info("Response status: {}",response.getStatus());
        log.info("Content Type: {}", response.getContentType());
        log.info("CharacterEncoding: {}",response.getCharacterEncoding());
        Collection<String> resHeaderNames = response.getHeaderNames();
        log.info("response header names: {}",resHeaderNames);


        filterChain.doFilter(request,response);

    }
}
