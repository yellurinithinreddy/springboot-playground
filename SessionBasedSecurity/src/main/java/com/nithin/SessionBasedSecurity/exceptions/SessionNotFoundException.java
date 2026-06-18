package com.nithin.SessionBasedSecurity.exceptions;

public class SessionNotFoundException extends RuntimeException{

    public SessionNotFoundException(String message){
        super(message);
    }
}
