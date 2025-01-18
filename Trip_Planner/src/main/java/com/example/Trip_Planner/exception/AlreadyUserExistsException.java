package com.example.Trip_Planner.error;

public class AlreadyUserExistsException extends RuntimeException{
    public AlreadyUserExistsException(String message) {
        super(message);
    }
}
