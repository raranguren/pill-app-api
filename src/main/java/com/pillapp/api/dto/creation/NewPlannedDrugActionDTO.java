package com.pillapp.api.dto.creation;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class NewPlannedDrugActionDTO {

    @NotNull
    public Long drugId;

    public Long doneTimestamp;

    @Length(max = 40, message = "Máximo {max} caracteres")
    public String dose;

}
