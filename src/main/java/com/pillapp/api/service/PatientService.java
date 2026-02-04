package com.pillapp.api.service;

import com.pillapp.api.domain.entities.Patient;
import com.pillapp.api.domain.entities.User;
import com.pillapp.api.dto.responses.data.PatientDTO;
import com.pillapp.api.dto.creation.NewPatientDTO;
import com.pillapp.api.dto.updates.FeedItemDTO;
import com.pillapp.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repository;
    private final UserAuthenticationService userAuthenticationService;
    private final ReminderService reminderService;
    private final CareActionService careActionService;
    @Autowired
    public PatientService(PatientRepository repository,
                          UserAuthenticationService userAuthenticationService,
                          ReminderService reminderService,
                          CareActionService careActionService) {
        this.repository = repository;
        this.userAuthenticationService = userAuthenticationService;
        this.reminderService = reminderService;
        this.careActionService = careActionService;
    }

    public PatientDTO create(NewPatientDTO dto){

        Patient patient = new Patient();
        patient.users = List.of(getLoggedUser());
        patient.fullName = dto.fullName;
        patient.notes = dto.notes;
        patient.kg = dto.kg;

        repository.save(patient);

        return patient.toDTO();
    }

    public List<FeedItemDTO> getUpdates(User user, long fromTimestamp) {

        var patients = repository.findAllByUsersContains(user);
        var updates = new ArrayList<FeedItemDTO>();

        for (var patient : patients){
            if (patient.updatedTimestamp > fromTimestamp) {
                updates.add(new FeedItemDTO(patient));
            }
            updates.addAll(reminderService.getUpdates(patient, fromTimestamp));
            updates.addAll(careActionService.getUpdates(patient, fromTimestamp));
        }

        return updates;
    }

    public Patient read(Long patientId) {
        return repository.findByIdAndUsersContains(patientId, getLoggedUser())
                .orElseThrow(()->new AccessDeniedException("Patient not found for logged user"));
    }

    private User getLoggedUser() {
        return userAuthenticationService.getLoggedUser();
    }

    public void assertIsVisibleToLoggedUser(Patient patient) {
        read(patient.id); // throws AccessDeniedException
    }
}
