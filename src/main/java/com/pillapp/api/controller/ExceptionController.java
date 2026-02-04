package com.pillapp.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.auth.login.FailedLoginException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        // When using @Valid annotation in a method parameter, Spring checks the
        // validations and throws MethodArgumentNotValidException when they fail.
        // Because there can be multiple validation errors, the exception has
        // a list of errors with a lot of internal details.

        // Simplify each one to: field name, error code and a default message
        var body = exception.getBindingResult()
                .getAllErrors().stream()
                .map(this::simplifyError)
                .toList();

        // And send the error 400 with the simplified array in the body
        return new ResponseEntity<>(body, headers, status);
    }

    private Map<?, ?> simplifyError(ObjectError error) {
        var response = new HashMap<String, String>();
        if (error instanceof FieldError) {
            response.put("field", ((FieldError)error).getField());
        }
        response.put("code", error.getCode());
        response.put("message", error.getDefaultMessage());
        return response;
    }

    // When login fails, just return 401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(FailedLoginException.class)
    public void handleFailedLogin() {}

}
