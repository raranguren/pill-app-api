package com.pillapp.api.repository;

import com.pillapp.api.domain.entities.Patient;
import com.pillapp.api.domain.parents.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findAllByPatientAndUpdatedTimestampGreaterThan(Patient patient, Long fromTimestamp);
}
