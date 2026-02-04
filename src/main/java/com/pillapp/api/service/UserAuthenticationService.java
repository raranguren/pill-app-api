package com.pillapp.api.service;

import com.pillapp.api.domain.entities.User;
import com.pillapp.api.domain.UserAuthentication;
import com.pillapp.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements UserDetailsService {

    private final UserRepository repository;
    @Autowired
    public UserAuthenticationService(UserRepository repository) {
        this.repository = repository;
    }

    // In our app the usernames are emails
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var search = repository.findByEmailIgnoreCase(email);
        if (search.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        return new UserAuthentication(search.get());
    }

    public Long getLoggedUserId() {
        var user = getLoggedUser();
        return user==null ? null : user.id;
    }

    public User getLoggedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            var principal = authentication.getPrincipal();
            if (principal instanceof UserAuthentication) {
                return (User)principal;
            } else {
                return null;
            }
        }
        return null;
    }

}
