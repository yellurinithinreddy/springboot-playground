package com.nithin.springRestAPi.springbootweb.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {

    @Builder.Default
//    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime timestamp = LocalDateTime.now();
    private T data;
    private ApiError apiError;

//    public ApiResponse(){
//        this.timestamp = LocalDateTime.now();
//    }
//
//    public ApiResponse(T data) {
//        this();
//        this.data = data;
//    }
//
//    public ApiResponse(ApiError apiError){
//        this();
//        this.apiError = apiError;
//    }
}
