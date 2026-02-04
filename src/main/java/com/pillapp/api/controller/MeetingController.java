package com.pillapp.api.controller;

import com.pillapp.api.dto.responses.data.MeetingDTO;
import com.pillapp.api.dto.creation.NewMeetingDTO;
import com.pillapp.api.service.MeetingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MeetingController {

    private final MeetingPlanService meetingPlanService;
    @Autowired
    public MeetingController(MeetingPlanService meetingPlanService) {
        this.meetingPlanService = meetingPlanService;
    }

    @PostMapping("meeting")
    @ResponseStatus(HttpStatus.CREATED)
    public MeetingDTO register(@RequestBody @Valid NewMeetingDTO meeting){
        return meetingPlanService.create(meeting);
    }
}
