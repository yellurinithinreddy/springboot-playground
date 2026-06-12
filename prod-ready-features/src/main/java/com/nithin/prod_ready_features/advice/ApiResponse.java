package com.nithin.prod_ready_features.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {


//    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private T data;
    private ApiError apiError;

}
