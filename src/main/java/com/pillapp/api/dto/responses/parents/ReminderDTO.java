package com.pillapp.api.dto.responses.parents;

import com.pillapp.api.domain.parents.Reminder;

public abstract class ReminderDTO {

    public Long id;
    public Long patientId;
    public Long reminderTimestamp;

    public ReminderDTO(Reminder reminder) {
        this.id = reminder.id;
        this.patientId = reminder.patient.id;
        this.reminderTimestamp = reminder.reminderTimestamp;
    }

}
