package com.pillapp.api.validator;

import com.pillapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserNameValidator implements
        ConstraintValidator<UniqueUserName, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(UniqueUserName contactNumber) {
    }

    @Override
    public boolean isValid(String email,
                           ConstraintValidatorContext context) {
        return !userService.userNameExists(email);
    }

}
