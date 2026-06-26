package com.nithin.SecurityApplication.advice;

import com.nithin.SecurityApplication.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.core.AuthenticationException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        ApiError apiError = ApiError.builder()
                .errorMessage(ex.getMessage())
                .errorCode(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> authenticationExceptionHandler(AuthenticationException ex){
        ApiError apiError = ApiError.builder()
                .errorMessage(ex.getMessage())
                .errorCode(HttpStatus.UNAUTHORIZED)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> jwtExceptionHanlder(JwtException ex){
        ApiError apiError = ApiError.builder()
                .errorMessage(ex.getMessage())
                .errorCode(HttpStatus.UNAUTHORIZED)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex){
        ApiError apiError = ApiError.builder()
                .errorMessage(ex.getLocalizedMessage())
                .errorCode(HttpStatus.FORBIDDEN)
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiError> handleAuthorizationDeniedException(AuthorizationDeniedException ex){
        ApiError apiError = ApiError.builder()
                .errorMessage(ex.getLocalizedMessage())
                .errorCode(HttpStatus.FORBIDDEN)
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

}
