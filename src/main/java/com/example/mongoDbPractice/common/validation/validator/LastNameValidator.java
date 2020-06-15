package com.example.mongoDbPractice.common.validation.validator;

import com.example.mongoDbPractice.common.utils.Constants;
import com.example.mongoDbPractice.common.utils.ValidationConstants;
import com.example.mongoDbPractice.common.validation.annotation.LastName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastNameValidator extends AbstractValidator implements ConstraintValidator<LastName,String> {
    @Override
    public void initialize(LastName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!value.trim().matches(ValidationConstants.REGEX_ALPHABETIC))
        {
           return addConstraintViolation(context, Constants.INCORRECT_LAST_NAME_EXCEPTION);
        }
        return true;
    }
}
