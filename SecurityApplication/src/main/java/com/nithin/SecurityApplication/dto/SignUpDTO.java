package com.nithin.SecurityApplication.dto;

import com.nithin.SecurityApplication.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email should be valid")
    private String email;

//    @NotBlank(message = "password is required")
    private String password;

    private Set<Role> roles;
}
