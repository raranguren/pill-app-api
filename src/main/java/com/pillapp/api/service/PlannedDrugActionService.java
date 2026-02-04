package com.pillapp.api.service;

import com.pillapp.api.domain.entities.DrugPlan;
import com.pillapp.api.domain.entities.PlannedDrugAction;
import com.pillapp.api.dto.creation.NewPlannedDrugActionDTO;
import com.pillapp.api.dto.responses.data.PlannedDrugActionDTO;
import com.pillapp.api.repository.PlannedDrugActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PlannedDrugActionService {

    private final PlannedDrugActionRepository repository;
    private final DrugPlanService planService;
    @Autowired
    public PlannedDrugActionService(PlannedDrugActionRepository repository, DrugPlanService planService) {
        this.repository = repository;
        this.planService = planService;
    }

    public PlannedDrugActionDTO create(NewPlannedDrugActionDTO dto) {
        DrugPlan plan = planService.read(dto.drugId);  // and verifies that current user has access
        if (dto.dose == null) dto.dose = plan.dosePerIntake;
        if (dto.doneTimestamp == null) dto.doneTimestamp = Instant.now().getEpochSecond();

        var entity = new PlannedDrugAction();
        entity.plan = plan;
        entity.patient = plan.patient;
        entity.doseNote = dto.dose;
        entity.doneTimestamp = dto.doneTimestamp;

        planService.updatePlan(plan, dto.doneTimestamp);
        return repository.save(entity).toDTO();
    }

}
