package com.nithin.SecurityApplication.services;


import com.nithin.SecurityApplication.entities.User;

public interface SessionService {

    void generateSession(User user, String refreshToken);

    void validateSession(String refreshToken);
}
