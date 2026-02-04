package com.pillapp.api.domain.entities;

import com.pillapp.api.domain.parents.Reminder;
import com.pillapp.api.dto.responses.ReminderPlanDTO;

import javax.persistence.Entity;

@Entity
public class ReminderPlan extends Reminder {

    public String description;

    @Override
    public String getType() {
        return "reminder";
    }

    @Override
    public Object toDTO() {
        return new ReminderPlanDTO(this);
    }
}
