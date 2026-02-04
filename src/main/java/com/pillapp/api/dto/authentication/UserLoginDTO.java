package com.pillapp.api.dto.authentication;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserLoginDTO {
    @Email
    @NotBlank
    public String email;

    @NotBlank
    public String password;



}
