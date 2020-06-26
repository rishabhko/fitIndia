package com.example.mongoDbPractice.common.validation.annotation;

import com.example.mongoDbPractice.common.validation.validator.LastNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LastNameValidator.class)
@NotBlank(message = "Last Name is required")
public @interface LastName {
    String message() default "Last Name is not correct.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
