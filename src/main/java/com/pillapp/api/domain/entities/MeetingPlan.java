package com.pillapp.api.domain.entities;

import com.pillapp.api.dto.responses.data.MeetingDTO;

import javax.persistence.Entity;

@Entity
public class MeetingPlan extends ReminderPlan {

    public Long appointmentTimestamp;

    @Override
    public String getType() {
        return "meeting";
    }

    @Override
    public Object toDTO() {
        return new MeetingDTO(this);
    }
}
