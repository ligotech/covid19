package com.creditsuisse.covid19.exception.handler;

public class Covid19Exception extends RuntimeException{

    public Covid19Exception(String message){
        super(message);
    }

    public Covid19Exception(String message, RuntimeException ex){
        super(message, ex);
    }
}
