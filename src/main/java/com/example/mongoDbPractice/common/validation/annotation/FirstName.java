package com.example.mongoDbPractice.common.validation.annotation;

import com.example.mongoDbPractice.common.validation.validator.FirstNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstNameValidator.class)
@NotBlank(message = "Name is required")
public @interface FirstName {
    String message() default "User Name is not correct.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
