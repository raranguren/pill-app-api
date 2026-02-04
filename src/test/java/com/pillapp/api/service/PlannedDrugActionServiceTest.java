package com.pillapp.api.service;

import com.pillapp.api.domain.entities.DrugPlan;
import com.pillapp.api.domain.entities.Patient;
import com.pillapp.api.domain.entities.PlannedDrugAction;
import com.pillapp.api.dto.creation.NewPlannedDrugActionDTO;
import com.pillapp.api.repository.PlannedDrugActionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlannedDrugActionServiceTest {

    @InjectMocks
    PlannedDrugActionService sut; // service under test

    @Mock
    DrugPlanService planService;

    @Mock
    PlannedDrugActionRepository repository;

    @Captor
    ArgumentCaptor<DrugPlan> planCaptor;

    @Test
    void when_create_action_then_updates_plan() {
        var patient = new Patient();
        patient.id = 2L;
        var plan = new DrugPlan();
        plan.id = 1L;
        plan.patient = patient;
        var dto = new NewPlannedDrugActionDTO();
        dto.drugId = 1L;
        var action = new PlannedDrugAction();
        action.patient = patient;
        action.plan = plan;
        when(planService.read(anyLong())).thenReturn(plan);
        when(repository.save(any())).thenReturn(action);
        sut.create(dto);
        verify(planService).updatePlan(planCaptor.capture(), anyLong());
        assertEquals(1L, planCaptor.getValue().id);
    }

}
