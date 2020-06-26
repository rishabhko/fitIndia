package com.example.mongoDbPractice.common.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String s) {
        super(s);
    }
}
