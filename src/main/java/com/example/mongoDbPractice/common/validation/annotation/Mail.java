package com.example.mongoDbPractice.common.validation.annotation;

import com.example.mongoDbPractice.common.validation.validator.MailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MailValidator.class)
@NotBlank(message = "Email is required")
public @interface Mail {
    String message() default "User email is not correct.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
