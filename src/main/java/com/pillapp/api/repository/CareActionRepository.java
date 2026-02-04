package com.pillapp.api.repository;

import com.pillapp.api.domain.entities.Patient;
import com.pillapp.api.domain.parents.CareAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareActionRepository extends JpaRepository<CareAction, Long> {
    List<CareAction> findAllByPatientAndUpdatedTimestampGreaterThan(Patient patient, long fromTimestamp);
}
