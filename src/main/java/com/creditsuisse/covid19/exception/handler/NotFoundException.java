package com.creditsuisse.covid19.exception.handler;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message){
        super(message);
    }
}
