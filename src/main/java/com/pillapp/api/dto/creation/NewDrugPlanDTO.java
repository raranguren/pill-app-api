package com.pillapp.api.dto.creation;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewDrugPlanDTO {

    @NotNull
    public Long patientId;

    @NotBlank
    @Length(max = 40, message = "Máximo {max} caracteres")
    public String description;

    @NotBlank
    @Length(max = 40, message = "Máximo {max} caracteres")
    public String dosePerIntake;

    @NotNull
    public Long period;

    public Long startTimestamp;

    public Long duration;

}
