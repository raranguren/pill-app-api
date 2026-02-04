package com.pillapp.api.service;

import com.pillapp.api.dto.updates.UpdatesFeedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UpdatesFeedService {

    private final PatientService patientService;
    private final UserAuthenticationService userAuthenticationService;
    @Autowired
    public UpdatesFeedService(PatientService patientService, UserAuthenticationService userAuthenticationService) {
        this.patientService = patientService;
        this.userAuthenticationService = userAuthenticationService;
    }

    public UpdatesFeedDTO getUpdates() {
        return getUpdates(0L);
    }

    @Transactional
    public UpdatesFeedDTO getUpdates(long fromTimestamp) {
        var loggedUser = userAuthenticationService.getLoggedUser();
        var updates = patientService.getUpdates(loggedUser, fromTimestamp);
        return new UpdatesFeedDTO(updates, fromTimestamp);
    }

}
