package com.pillapp.api.dto.responses;

import com.pillapp.api.domain.entities.ReminderPlan;
import com.pillapp.api.dto.responses.parents.ReminderDTO;

public class ReminderPlanDTO extends ReminderDTO {

    public String description;

    public ReminderPlanDTO(ReminderPlan reminderPlan) {
        super(reminderPlan);
        this.description = reminderPlan.description;
    }
}
