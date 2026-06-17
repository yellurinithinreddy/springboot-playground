package com.nithin.SecurityApplication.advice;

import com.nithin.SecurityApplication.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiError> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        ApiError apiError = ApiError.builder()
                .errorMessage(ex.getMessage())
                .errorCode(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
}
