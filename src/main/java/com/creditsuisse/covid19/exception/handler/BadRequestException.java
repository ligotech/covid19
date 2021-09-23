package com.creditsuisse.covid19.exception.handler;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message){
        super(message);
    }
}
