package com.pillapp.api.controller;

import com.pillapp.api.dto.updates.UpdatesFeedDTO;
import com.pillapp.api.service.UpdatesFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdatesFeedController {

    private final UpdatesFeedService service;
    @Autowired
    public UpdatesFeedController(UpdatesFeedService service) {
        this.service = service;
    }

    @GetMapping({"", "updates", "feed"})
    public UpdatesFeedDTO updates(@RequestParam(name = "t", required = false, defaultValue = "0") long fromTimestamp){
        return service.getUpdates(fromTimestamp);
    }

}
