package com.pillapp.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenServiceTest {

    static final String DEFAULT_JWT_SECRET = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    @InjectMocks
    JwtTokenService service;

    @Test
    void from_username_to_token_to_username() {
        var username = "Pepito";
        ReflectionTestUtils.setField(service, "secret", DEFAULT_JWT_SECRET);
        var token = service.generateToken(username);
        var isValid = service.isValidToken(token);
        var result = service.getUsername(token);
        assertNotNull(token);
        assertTrue(isValid);
        assertEquals(result, username);
    }

}
