package com.example.bookmyshow.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long userId){
        super("User with Id - "+userId+" does not exist.");
    }
}
