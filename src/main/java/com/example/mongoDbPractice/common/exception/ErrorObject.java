package com.example.mongoDbPractice.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.HttpStatus;

public class ErrorObject {
    private HttpStatus error;
    private String ex;
    private String customMessage;
    @JsonInclude
    ValidationErrors validationErrors;

    public ErrorObject(HttpStatus error, String ex, String customMessage, ValidationErrors validationErrors) {
        this.error = error;
        this.ex = ex;
        this.customMessage = customMessage;
        this.validationErrors = validationErrors;
    }

    public ErrorObject(HttpStatus error, String ex, String customMessage) {
        this.error = error;
        this.ex = ex;
        this.customMessage = customMessage;
    }

    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(ValidationErrors validationErrors) {
        this.validationErrors = validationErrors;
    }

    public HttpStatus getError() {
        return error;
    }

    public void setError(HttpStatus error) {
        this.error = error;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }
}
