package com.pillapp.api.domain.entities;

import com.pillapp.api.domain.parents.CarePlan;
import com.pillapp.api.dto.responses.data.DrugPlanDTO;

import javax.persistence.Entity;

@Entity
public class DrugPlan extends CarePlan {

    public String description;
    public String dosePerIntake;

    @Override
    public String getType() {
        return "drug";
    }

    @Override
    public Object toDTO() {
        return new DrugPlanDTO(this);
    }
}
