package com.pillapp.api.service;

import com.pillapp.api.dto.authentication.UserRegisterDTO;
import com.pillapp.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void when_create_user_then_uses_repository() {
        var dto = new UserRegisterDTO();
        dto.email = "test@mail.com";
        dto.userName = "username";
        dto.password = "asdfaba";
        service.create(dto);
        verify(repository).save(any());
        verify(passwordEncoder).encode(any());
    }

}
