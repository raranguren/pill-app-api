package com.pillapp.api.service;

import com.pillapp.api.domain.entities.DrugPlan;
import com.pillapp.api.domain.entities.Patient;
import com.pillapp.api.dto.creation.NewDrugPlanDTO;
import com.pillapp.api.repository.DrugPlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DrugPlanServiceTest {

    @InjectMocks
    DrugPlanService service;

    @Mock
    DrugPlanRepository repository;

    @Mock
    PatientService patientService;

    @Captor
    ArgumentCaptor<DrugPlan> captor;

    @Test
    void when_create_then_success() {
        var dto = new NewDrugPlanDTO();
        dto.patientId = 1L;
        var patient = new Patient();
        patient.id = 1L;
        var action = new DrugPlan();
        action.patient = patient;
        when(patientService.read(1L)).thenReturn(patient);
        when(repository.save(any())).thenReturn(action);
        service.create(dto);
        verify(repository).save(captor.capture());
        assertEquals(1L, captor.getValue().patient.id);
    }

    @Test
    void when_read_then_success() {
        var plan = new DrugPlan();
        plan.id = 1L;
        plan.patient = new Patient();
        plan.patient.id = 2L;
        when(repository.findById(1L)).thenReturn(Optional.of(plan));
        service.read(1L);
        verify(repository).findById(1L);
    }


    static final long NOW = Instant.now().getEpochSecond();
    static final long EIGHT_HOURS = 60L * 60 * 8;
    static final long YESTERDAY = NOW - 60L * 60 * 24;
    static final long A_WEEK = 60L * 60 * 24 * 7;

    @Test
    void when_update_stopped_plan_then_do_nothing() {
        var plan = new DrugPlan();
        plan.stop();
        service.updatePlan(plan, NOW);
        verifyNoInteractions(repository);
        assert (plan.isStopped());
    }

    @Test
    void when_update_new_plan_then_start() {
        var plan = new DrugPlan();
        assert (!plan.isStarted());
        service.updatePlan(plan, NOW);
        assert (plan.isStarted());
    }

    @Test
    void when_update_plan_then_applies_period() {
        var plan = new DrugPlan();
        plan.startFrom(YESTERDAY);
        plan.period = EIGHT_HOURS;
        service.updatePlan(plan, NOW);
        assertEquals(NOW + plan.period, plan.reminderTimestamp);
    }

    @Test
    void when_update_plan_with_no_duration_then_success() {
        var plan = new DrugPlan();
        plan.period = EIGHT_HOURS;
        service.updatePlan(plan, NOW);
        assertEquals(NOW + plan.period, plan.reminderTimestamp);
    }

    @Test
    void when_update_plan_within_duration_then_continue() {
        var plan = new DrugPlan();
        plan.startFrom(YESTERDAY);
        plan.period = EIGHT_HOURS;
        plan.duration = A_WEEK;
        service.updatePlan(plan, NOW);
        assertEquals(NOW + plan.period, plan.reminderTimestamp);
    }

    @Test
    void when_update_plan_then_stops() {
        var plan = new DrugPlan();
        plan.startFrom(NOW - A_WEEK);
        plan.period = EIGHT_HOURS;
        plan.duration = A_WEEK;
        service.updatePlan(plan, NOW);
        assert(plan.isStopped());
    }

}
