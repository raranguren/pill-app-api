package com.pillapp.api.dto.responses.data;

import com.pillapp.api.domain.entities.DrugPlan;
import com.pillapp.api.dto.responses.parents.ReminderDTO;

public class DrugPlanDTO extends ReminderDTO {

    public String description;
    public String dosePerIntake;
    public Long period;
    public Long startTimestamp;
    public Long duration;

    public DrugPlanDTO(DrugPlan drugPlan) {
        super(drugPlan);
        this.description = drugPlan.description;
        this.dosePerIntake = drugPlan.dosePerIntake;
        this.period = drugPlan.period;
        this.startTimestamp = drugPlan.startTimestamp;
        this.duration = drugPlan.duration;
    }
}
