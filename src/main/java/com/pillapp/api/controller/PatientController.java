package com.pillapp.api.controller;

import com.pillapp.api.dto.responses.data.PatientDTO;
import com.pillapp.api.dto.creation.NewPatientDTO;
import com.pillapp.api.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PatientController {

    private final PatientService patientService;
    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("patient")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO register(@RequestBody @Valid NewPatientDTO patient) {
        return patientService.create(patient);
    }


}
