package com.example.mongoDbPractice.common.validation.validator;

import com.example.mongoDbPractice.common.utils.Constants;
import com.example.mongoDbPractice.common.utils.ValidationConstants;
import com.example.mongoDbPractice.common.validation.annotation.Mail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MailValidator extends AbstractValidator implements ConstraintValidator<Mail,String>{
    @Override
    public void initialize(Mail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!value.trim().matches(ValidationConstants.REGEX_MAIL))
        {
            return addConstraintViolation(context, Constants.INCORRECT_MAIL_EXCEPTION);
        }
        return true;
    }
}
