package com.nithin.CMS.CollegeManagementSystem.advice;

import com.nithin.CMS.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<?> handleResourceNotFound(ResourceNotFoundException e){
       ApiError apiError = ApiError
               .builder()
               .message(e.getLocalizedMessage())
               .build();

       return buildAPIResponse(apiError);

   }

   public ApiResponse<?> buildAPIResponse(ApiError apiError){

       return ApiResponse
               .builder()
               .apiError(apiError)
               .build();
   }
}
