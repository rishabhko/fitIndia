package com.example.mongoDbPractice.common.validation.validator;

import com.example.mongoDbPractice.common.utils.Constants;
import com.example.mongoDbPractice.common.utils.ValidationConstants;
import com.example.mongoDbPractice.common.validation.annotation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator extends AbstractValidator implements ConstraintValidator<PhoneNumber,String> {
    @Override
    public void initialize(PhoneNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!value.trim().matches(ValidationConstants.REGEX_NUMERIC))
        {
            return addConstraintViolation(context, Constants.INCORRECT_PHONE_NUMBER_EXCEPTION);
        }
        return true;
    }
}
