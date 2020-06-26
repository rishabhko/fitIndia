package com.example.mongoDbPractice.common.validation.validator;

import com.example.mongoDbPractice.common.utils.Constants;
import com.example.mongoDbPractice.common.utils.ValidationConstants;
import com.example.mongoDbPractice.common.validation.annotation.UserID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class UserIDValidator extends AbstractValidator implements ConstraintValidator<UserID,String> {

    @Override
    public void initialize(UserID constraintAnnotation){
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!value.trim().matches(ValidationConstants.REGEX_ALPHANUMERIC))
        {
            return addConstraintViolation(context, Constants.INCORRECT_USERID_EXCEPTION);
        }
        return true;
    }
}
