package com.pillapp.api.dto.authentication;

import com.pillapp.api.validator.UniqueEmail;
import com.pillapp.api.validator.UniqueUserName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserRegisterDTO {

    @NotBlank
    @Email
    @Length(max = 40, message = "Máximo {max} caracteres")
    @UniqueEmail(message = "Correo ya registrado por otro usuario")
    public String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])(?=\\S+$).{1,}$", message = "Debe contener una letra mayuscula, un numero y un caracter especial")
    @Length(max = 20, min = 8, message = "Entre {min} y {max} caracteres")
    @NotBlank
    public String password;

    @NotBlank
    @Pattern(regexp = "\\w*", message = "Solo puede contener letras y numeros")
    @Length(max = 20, min = 3, message = "Entre {min} y {max} caracteres")
    @UniqueUserName(message = "Nombre ocupado por otro usuario")
    public String userName;

}
