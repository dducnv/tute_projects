package com.example.tute_backend.exception;

public class ApiFormException extends RuntimeException{
    public ApiFormException(String message){
        super(message);
    }
    public ApiFormException(String message,Throwable cause){
        super(message,cause);
    }
}
