package com.pillapp.api.service;

import com.pillapp.api.domain.entities.MeetingPlan;
import com.pillapp.api.domain.entities.Patient;
import com.pillapp.api.dto.creation.NewMeetingDTO;
import com.pillapp.api.repository.MeetingPlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeetingPlanServiceTest {

    @InjectMocks
    MeetingPlanService service;

    @Mock
    MeetingPlanRepository repository;

    @Mock
    PatientService patientService;

    @Test
    void when_created_then_entity_has_no_null_values() {
        var givenDto = new NewMeetingDTO();
        givenDto.appointmentTimestamp = Instant.now().plus(5, ChronoUnit.DAYS).getEpochSecond();
        givenDto.patientId = 1L;
        givenDto.description = "test cita médica";

        when(patientService.read(any())).thenReturn(new Patient());

        service.create(givenDto);

        var captor = ArgumentCaptor.forClass(MeetingPlan.class);
        verify(repository).save(captor.capture());
        var entity = captor.getValue();
        assertNotNull(entity.appointmentTimestamp);
        assertNotNull(entity.patient);
        assertNotNull(entity.description);
        assertNotNull(entity.reminderTimestamp);
        assertNotNull(entity.updatedTimestamp);
    }

    @Test
    void when_create_and_patient_not_found_then_return_403() {
        when(patientService.read(any())).thenThrow(new AccessDeniedException("wrong patient id"));
        Executable action = () -> service.create(new NewMeetingDTO());
        assertThrows(AccessDeniedException.class, action);
        verifyNoInteractions(repository);
    }

}
