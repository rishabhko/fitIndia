package com.example.mongoDbPractice.common.validation.annotation;

import com.example.mongoDbPractice.common.validation.validator.UserIDValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserIDValidator.class)
@NotBlank(message = "UserID is required")
public @interface UserID {
    String message() default "User ID is not correct.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
