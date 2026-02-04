package com.pillapp.api.repository;

import com.pillapp.api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUserNameIgnoreCase(String userName);

    Optional<User> findByEmailIgnoreCase(String email);

}
