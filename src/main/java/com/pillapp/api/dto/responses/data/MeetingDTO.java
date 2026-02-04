package com.pillapp.api.dto.responses.data;

import com.pillapp.api.domain.entities.MeetingPlan;
import com.pillapp.api.dto.responses.parents.ReminderDTO;

public class MeetingDTO extends ReminderDTO {

    public Long appointmentTimestamp;
    public String description;

    public MeetingDTO(MeetingPlan meetingPlan) {
        super(meetingPlan);
        this.appointmentTimestamp = meetingPlan.appointmentTimestamp;
        this.description = meetingPlan.description;
    }
}
