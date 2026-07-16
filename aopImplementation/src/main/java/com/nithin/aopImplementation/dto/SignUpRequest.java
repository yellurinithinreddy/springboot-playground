package com.nithin.aopImplementation.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank
       String name,
       @Email
       @NotBlank
       String email,

       @NotBlank
       @Size(min = 8,max = 13)
       String password
) {

}
