package com.pillapp.api.repository;

import com.pillapp.api.domain.entities.MeetingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingPlanRepository extends JpaRepository<MeetingPlan, Long> {
}
