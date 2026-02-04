package com.pillapp.api.service;

import com.pillapp.api.domain.entities.Patient;
import com.pillapp.api.dto.updates.FeedItemDTO;
import com.pillapp.api.repository.CareActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CareActionService {

    private final CareActionRepository repository;
    @Autowired
    public CareActionService(CareActionRepository repository) {
        this.repository = repository;
    }

    public List<FeedItemDTO> getUpdates(Patient patient, long fromTimestamp) {
        var actions = repository.findAllByPatientAndUpdatedTimestampGreaterThan(patient, fromTimestamp);
        var updates = new ArrayList<FeedItemDTO>();

        for (var action : actions) {
            updates.add(new FeedItemDTO(action));
        }

        return updates;
    }

}
