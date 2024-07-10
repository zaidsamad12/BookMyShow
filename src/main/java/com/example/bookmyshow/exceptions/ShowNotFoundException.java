package com.example.bookmyshow.exceptions;

public class ShowNotFoundException extends RuntimeException{

    public ShowNotFoundException(Long showId){
        super("Show with showId - "+showId+" does not exist");

    }}
