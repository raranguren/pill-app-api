package com.pillapp.api.repository;

import com.pillapp.api.domain.entities.DrugPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugPlanRepository extends JpaRepository<DrugPlan, Long> {
}
