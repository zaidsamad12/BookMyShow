package com.example.bookmyshow.exceptions;

public class ShowSeatNotAvailableException extends Exception{
    public ShowSeatNotAvailableException(Long id) {
        super("Seat" +id+" not available exception");
    }
}
