package com.example.mongoDbPractice.common.validation.annotation;

import com.example.mongoDbPractice.common.validation.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@NotBlank(message = "Phone Number is required")
public @interface PhoneNumber {
    String message() default "User phone number is not correct.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
