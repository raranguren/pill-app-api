package com.pillapp.api.service;

import com.pillapp.api.domain.entities.MeetingPlan;
import com.pillapp.api.dto.responses.data.MeetingDTO;
import com.pillapp.api.dto.creation.NewMeetingDTO;
import com.pillapp.api.repository.MeetingPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MeetingPlanService {

    private final MeetingPlanRepository repository;
    private final PatientService patientService;
    @Autowired
    public MeetingPlanService(MeetingPlanRepository repository, PatientService patientService) {
        this.repository = repository;
        this.patientService = patientService;
    }

    public MeetingDTO create(NewMeetingDTO dto) {

        MeetingPlan meetingPlan = new MeetingPlan();
        meetingPlan.patient = patientService.read(dto.patientId);
        meetingPlan.appointmentTimestamp = dto.appointmentTimestamp;
        meetingPlan.description = dto.description;
        meetingPlan.reminderTimestamp = dto.reminderTimestamp;

        if(meetingPlan.reminderTimestamp == null){
            meetingPlan.reminderTimestamp = Instant.now().getEpochSecond();
        }

        repository.save(meetingPlan);

        return new MeetingDTO(meetingPlan);
    }

}