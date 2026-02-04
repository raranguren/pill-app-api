package com.pillapp.api.service;

import com.pillapp.api.domain.entities.DrugPlan;
import com.pillapp.api.dto.responses.data.DrugPlanDTO;
import com.pillapp.api.dto.creation.NewDrugPlanDTO;
import com.pillapp.api.repository.DrugPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DrugPlanService {

    private final DrugPlanRepository repository;
    private final PatientService patientService;

    @Autowired
    public DrugPlanService(DrugPlanRepository repository, PatientService patientService){
        this.repository = repository;
        this.patientService = patientService;
    }

    public DrugPlanDTO create(NewDrugPlanDTO dto){

        DrugPlan drugPlan = new DrugPlan();
        drugPlan.patient = patientService.read(dto.patientId);
        drugPlan.description = dto.description;
        drugPlan.dosePerIntake = dto.dosePerIntake;
        drugPlan.period = dto.period;
        drugPlan.startTimestamp = dto.startTimestamp;
        drugPlan.duration = dto.duration;

        if(drugPlan.reminderTimestamp == null){
            drugPlan.reminderTimestamp = Instant.now().getEpochSecond();
        }

        repository.save(drugPlan);

        return new DrugPlanDTO(drugPlan);
    }

    public DrugPlan read(Long id) {
        var plan = repository.findById(id)
                .orElseThrow(()-> new AccessDeniedException("Plan not found"));
        patientService.assertIsVisibleToLoggedUser(plan.patient);
        return plan;
    }

    public void updatePlan(DrugPlan plan, Long actionTimestamp) {
        if (plan.isStopped()) return;
        if (!plan.isStarted()) {
            plan.startFrom(actionTimestamp);
        }
        plan.reminderTimestamp = actionTimestamp + plan.period;
        if (plan.hasDuration()) {
            if (plan.reminderTimestamp > plan.startTimestamp + plan.duration) {
                plan.stop();
            }
        }
        repository.save(plan);
    }

}
