package com.nithin.CachingApp.advice;


import com.nithin.CachingApp.exception.ResourceNotFoundException;
import org.hibernate.StaleStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){
        return ResponseEntity.notFound().build();
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<?> handleRuntimeException(RuntimeException ex){
//        return ResponseEntity.internalServerError().build();
//    }

    @ExceptionHandler(StaleStateException.class)
    public ResponseEntity<?> handleRuntimeException(StaleStateException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Stale object \n");
    }
}
