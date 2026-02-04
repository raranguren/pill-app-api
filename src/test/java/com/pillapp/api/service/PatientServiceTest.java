package com.pillapp.api.service;

import com.pillapp.api.domain.entities.Patient;
import com.pillapp.api.domain.entities.User;
import com.pillapp.api.dto.creation.NewPatientDTO;
import com.pillapp.api.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @InjectMocks
    PatientService service;

    @Mock
    PatientRepository repository;

    @Mock
    UserAuthenticationService authenticationService;

    @Test
    void when_create_then_uses_repository() {
        var userId = 1L;
        var user = new User(userId, "testr@mail.com", "testuser", "Test.123");
        when(authenticationService.getLoggedUser()).thenReturn(user);
        service.create(new NewPatientDTO());
        verify(repository).save(any());
    }

    @Test
    void when_create_then_includes_logged_user() {
        var userId = 1L;
        var user = new User(userId, "testr@mail.com", "testuser", "Test.123");
        when(authenticationService.getLoggedUser()).thenReturn(user);
        service.create(new NewPatientDTO());
        var captor = ArgumentCaptor.forClass(Patient.class);
        verify(repository).save(captor.capture());
        assertEquals(userId, captor.getValue().users.get(0).id);
    }

    @Test
    void when_user_adds_patient_then_only_that_user_can_see_it() {
        var userId = 2L;
        var user = new User(userId, "test2@mail.com", "test2", "Test.123");
        service.getUpdates(user, 0L);
        var captor = ArgumentCaptor.forClass(User.class);
        verify(repository).findAllByUsersContains(captor.capture());
        assertEquals(userId, captor.getValue().id);
    }

    @Test
    void when_find_patient_for_another_user_then_return_403() {
        Executable action = () -> service.read(4L);
        assertThrows(AccessDeniedException.class, action);
        verify(repository, times(0)).findById(any());
        verify(repository, times(1)).findByIdAndUsersContains(any(), any());
    }


}
