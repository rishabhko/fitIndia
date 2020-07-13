package com.example.mongoDbPractice.common.validation.validator;

import com.example.mongoDbPractice.common.utils.Constants;
import com.example.mongoDbPractice.common.utils.ValidationConstants;
import com.example.mongoDbPractice.common.validation.annotation.FirstName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FirstNameValidator extends AbstractValidator implements ConstraintValidator<FirstName,String> {
    @Override
    public void initialize(FirstName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!value.trim().matches(ValidationConstants.REGEX_ALPHABETIC))
        {
            System.out.println("invalid");
            return addConstraintViolation(context, Constants.INCORRECT_FIRST_NAME_EXCEPTION);
        }
        return true;
    }
}
