package com.nithin.prod_ready_features.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    private HttpStatus httpStatus;
}
