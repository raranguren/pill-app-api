package com.pillapp.api.dto.creation;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class NewPatientDTO {

    @NotBlank
    @Length(max = 40, message = "Máximo {max} caracteres")
    @Pattern(regexp = "[^\\r^\\n]+", message = "No debe tener saltos de linea")
    public String fullName;

    @Length(max = 40, message = "Máximo {max} caracteres")
    public String notes;

    @Positive(message = "Debe ser mayor de cero")
    public Integer kg;
}
