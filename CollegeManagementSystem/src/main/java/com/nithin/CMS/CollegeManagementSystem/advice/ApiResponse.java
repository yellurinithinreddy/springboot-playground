package com.nithin.CMS.CollegeManagementSystem.advice;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private T data;

    private ApiError apiError;
}
