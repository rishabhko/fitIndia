package com.example.mongoDbPractice.common.exception;

public class OtpNotVerifiedException extends RuntimeException {
    public OtpNotVerifiedException(String s) {
        super(s);
    }
}
