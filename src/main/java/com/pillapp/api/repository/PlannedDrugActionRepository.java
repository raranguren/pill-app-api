package com.pillapp.api.repository;

import com.pillapp.api.domain.entities.PlannedDrugAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannedDrugActionRepository extends JpaRepository<PlannedDrugAction, Long> {
}
