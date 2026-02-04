package com.pillapp.api.validator;

import com.pillapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements
        ConstraintValidator<UniqueEmail, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(UniqueEmail contactNumber) {
    }

    @Override
    public boolean isValid(String email,
                           ConstraintValidatorContext context) {
        return !userService.emailExists(email);
    }

}