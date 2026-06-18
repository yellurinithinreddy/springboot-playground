package com.nithin.SessionBasedSecurity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    @NotBlank(message = "name is required to signup")
    private String name;

    @NotBlank(message = "email is required to signup")
    @Email
    private String email;

    @NotBlank(message = "password is required to signup")
    private String password;
}
