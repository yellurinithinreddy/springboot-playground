package com.nithin.SessionBasedSecurity.advice;

import com.nithin.SessionBasedSecurity.exceptions.InvalidTokenException;
import com.nithin.SessionBasedSecurity.exceptions.ResourceNotFoundException;
import com.nithin.SessionBasedSecurity.exceptions.SessionNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex){
        ApiError apiError = ApiError.builder()
                .errorCode(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException ex){
        ApiError apiError = ApiError.builder()
                .errorCode(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<ApiError> handleSessionNotFoundException(SessionNotFoundException ex){
        ApiError apiError = ApiError.builder()
                .errorCode(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleSessionNotFoundException(AuthenticationException ex){
        ApiError apiError = ApiError.builder()
                .errorCode(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException ex){
        ApiError apiError = ApiError.builder()
                .errorCode(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }



}
