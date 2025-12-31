package com.lucastexeira.authuser.core.exception;

public class UserAlreadyExistsException extends  RuntimeException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
