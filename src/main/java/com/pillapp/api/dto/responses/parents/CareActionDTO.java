package com.pillapp.api.dto.responses.parents;

import com.pillapp.api.domain.parents.CareAction;

public abstract class CareActionDTO {

    public Long id;
    public Long patientId;
    public Long doneTimestamp;

    public CareActionDTO(CareAction careAction) {
        this.id = careAction.id;
        this.patientId = careAction.patient.id;
        this.doneTimestamp = careAction.doneTimestamp;
    }
}
