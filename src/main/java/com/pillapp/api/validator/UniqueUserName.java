package com.pillapp.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUserNameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserName {
    String message() default "Ya existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
