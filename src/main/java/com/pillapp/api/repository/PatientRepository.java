package com.pillapp.api.repository;

import com.pillapp.api.domain.entities.Patient;
import com.pillapp.api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {


    List<Patient> findAllByUsersContains(User loggedUser);

    Optional<Patient> findByIdAndUsersContains(Long patientId, User loggedUser);
}
