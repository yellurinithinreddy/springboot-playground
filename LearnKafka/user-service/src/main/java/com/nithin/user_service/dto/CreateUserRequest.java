package com.nithin.user_service.dto;

import lombok.Data;

@Data
public class CreateUserRequest {

    private Long id;
    private String name;
    private String email;
}
