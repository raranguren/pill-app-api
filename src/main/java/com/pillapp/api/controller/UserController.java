package com.pillapp.api.controller;

import com.pillapp.api.dto.authentication.UserLoginDTO;
import com.pillapp.api.dto.authentication.UserLoginSuccessDTO;
import com.pillapp.api.dto.authentication.UserRegisterDTO;
import com.pillapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.FailedLoginException;
import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@RequestBody @Valid UserRegisterDTO user) {
        userService.create(user);
    }

    @PostMapping("login")
    public UserLoginSuccessDTO login(@RequestBody @Valid UserLoginDTO credentials) throws FailedLoginException {
        return userService.login(credentials);
    }
}
