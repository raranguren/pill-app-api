package com.pillapp.api.service;

import com.pillapp.api.domain.entities.Patient;
import com.pillapp.api.dto.updates.FeedItemDTO;
import com.pillapp.api.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReminderService {

    private final ReminderRepository repository;
    @Autowired
    public ReminderService(ReminderRepository repository) {
        this.repository = repository;
    }

    public List<FeedItemDTO> getUpdates(Patient patient, long fromTimestamp) {
        var reminders = repository.findAllByPatientAndUpdatedTimestampGreaterThan(patient, fromTimestamp);
        var updates = new ArrayList<FeedItemDTO>();

        for (var reminder : reminders){
            updates.add(new FeedItemDTO(reminder));
        }

        return updates;
    }
}
