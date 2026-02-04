package com.pillapp.api.dto.responses.data;

import com.pillapp.api.domain.entities.PlannedDrugAction;
import com.pillapp.api.dto.responses.parents.CareActionDTO;

public class PlannedDrugActionDTO extends CareActionDTO {

    public Long drugId;
    public String dose;

    public PlannedDrugActionDTO(PlannedDrugAction plannedDrugAction) {
        super(plannedDrugAction);
        drugId = plannedDrugAction.plan.id;
        dose = plannedDrugAction.doseNote;
    }
}
