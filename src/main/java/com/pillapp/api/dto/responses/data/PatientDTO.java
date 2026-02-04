package com.pillapp.api.dto.responses.data;

import com.pillapp.api.domain.entities.Patient;

import java.util.List;

public class PatientDTO {

    public Long id;
    public String fullName;
    public String notes;
    public Integer kg;
    public List<UserProfileDTO> userProfiles;

    public PatientDTO(Patient patient) {
        this.id = patient.id;
        this.fullName = patient.fullName;
        this.notes = patient.notes;
        this.kg = patient.kg;
        this.userProfiles = patient.users.stream().map(UserProfileDTO::new).toList();
    }
}
