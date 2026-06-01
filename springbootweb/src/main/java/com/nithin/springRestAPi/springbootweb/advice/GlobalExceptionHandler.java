package com.nithin.springRestAPi.springbootweb.advice;

import com.nithin.springRestAPi.springbootweb.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
//        List<String> errors =  exception
//                .getBindingResult()
//                .getAllErrors()
//                .stream()
//                .map(error -> error.getDefaultMessage())
//                .toList();
//
//        ApiError apiError = ApiError.
//                builder()
//                .message("Input Validation failed")
//                .status(HttpStatus.BAD_REQUEST)
//                .subErrors(errors)
//                .build();
//
//        return new ResponseEntity<>(apiError,apiError.getStatus());
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleServerError(Exception exception){
        ApiError apiError = ApiError
                .builder()
                .message(exception.getLocalizedMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(apiError,apiError.getStatus());
    }



}
