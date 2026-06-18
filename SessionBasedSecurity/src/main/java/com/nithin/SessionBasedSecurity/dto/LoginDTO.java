package com.nithin.SessionBasedSecurity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "email is required to login")
    private String email;

    @NotBlank(message = "password is required to login")
    private String password;
}
