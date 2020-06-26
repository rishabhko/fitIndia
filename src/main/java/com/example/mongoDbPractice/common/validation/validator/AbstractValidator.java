package com.example.mongoDbPractice.common.validation.validator;

import javax.validation.ConstraintValidatorContext;

public abstract class AbstractValidator {
    boolean addConstraintViolation(ConstraintValidatorContext context, String message)
    {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
