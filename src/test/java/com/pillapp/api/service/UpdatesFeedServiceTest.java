package com.pillapp.api.service;

import com.pillapp.api.domain.entities.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdatesFeedServiceTest {

    @InjectMocks
    UpdatesFeedService service;

    @Mock
    UserAuthenticationService authenticationService;

    @Mock
    PatientService patientService;

    @Test
    void when_get_updates_then_has_timestamp_and_array() {
        var result = service.getUpdates();
        assertNotNull(result.lastUpdate);
        assertNotNull(result.updates);
    }

    @Disabled
    @Test
    void when_get_updates_then_uses_logged_user() {
        // TODO: ver qué pasa
        var user = new User();
        user.id = 13L;
        when(authenticationService.getLoggedUser()).thenReturn(user);
        service.getUpdates();
        var captor = ArgumentCaptor.forClass(User.class);
        verify(patientService).getUpdates(captor.capture(), anyLong());
        assertEquals(user.id, captor.getValue().id);
    }

}
