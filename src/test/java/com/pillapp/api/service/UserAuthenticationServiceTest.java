package com.pillapp.api.service;

import com.pillapp.api.domain.entities.User;
import com.pillapp.api.domain.UserAuthentication;
import com.pillapp.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAuthenticationServiceTest {

    @InjectMocks
    UserAuthenticationService service;

    @Mock
    UserRepository repository;

    @Test
    void loads_users_by_email() {
        var email = "abc@mail.com";
        var user = new User();
        user.email = email;
        when(repository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(user));
        var userDetails = service.loadUserByUsername(email);
        assertEquals(userDetails.getUsername(), email);
    }

    @Test
    void when_user_not_found_then_exception() {
        var email = "xyz@mail.com";
        when(repository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());
        Executable action = () -> service.loadUserByUsername(email);
        assertThrows(UsernameNotFoundException.class, action);
    }

    @Test
    void when_login_then_principal_has_id() {
        var id = 123L;
        var email = "withid@mail.com";
        var userName = "withid";
        var password = "Test.123";
        var user = new User(id,email,userName,password);
        when(repository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));
        var result = service.loadUserByUsername(email);
        assertInstanceOf(UserAuthentication.class, result);
        assertEquals(id, ((UserAuthentication) result).id);
    }

    @Test
    void when_logged_then_can_get_user_id() {
        var id = 123L;
        var email = "withid@mail.com";
        var userName = "withid";
        var password = "Test.123";
        var user = new User(id,email,userName,password);
        var authentication = new UsernamePasswordAuthenticationToken(
                new UserAuthentication(user),null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var result = service.getLoggedUserId();
        assertEquals(id, result);
    }

    @Test
    void when_logged_then_can_get_user() {
        var id = 123L;
        var email = "withid@mail.com";
        var userName = "user";
        var password = "Test.123";
        var user = new User(id,email,userName,password);
        var authentication = new UsernamePasswordAuthenticationToken(
                new UserAuthentication(user),null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var result = service.getLoggedUser();
        assertInstanceOf(User.class, result);
        assertEquals(id, result.id);
        assertEquals(email, result.email);
        assertEquals(userName, result.userName);
    }

}
