package com.pillapp.api.dto.creation;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewMeetingDTO {

    @NotNull
    public Long patientId;

    @NotNull
    public Long appointmentTimestamp;

    @NotBlank
    @Length(max = 40, message = "Máximo {max} caracteres")
    public String description;

    public Long reminderTimestamp;

}
