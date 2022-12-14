package com.nhnacademy.edu.minidoorayaccount.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String usernameOrEmail) {
        super("User Not Found : " + usernameOrEmail);
    }
}
