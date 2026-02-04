package com.pillapp.api.domain.entities;

import com.pillapp.api.domain.parents.DrugAction;
import com.pillapp.api.dto.responses.data.PlannedDrugActionDTO;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class PlannedDrugAction extends DrugAction {

    @OneToOne
    public DrugPlan plan;

    @Override
    public String getType() {
        return "drugAction";
    }

    @Override
    public PlannedDrugActionDTO toDTO() {
        return new PlannedDrugActionDTO(this);
    }
}
