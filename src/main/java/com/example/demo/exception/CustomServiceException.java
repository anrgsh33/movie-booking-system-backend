package com.example.demo.exception;

public class CustomServiceException extends RuntimeException{
    public CustomServiceException(String message) {
        super(message);
    }
}
