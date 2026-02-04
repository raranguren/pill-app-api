package com.pillapp.api.domain.parents;

import javax.persistence.Entity;

@Entity
public abstract class CarePlan extends Reminder {

    public Long startTimestamp;
    public Long duration;
    public Long period = 3600L; // default 1h for easy testing, but the value is mandatory in creation DTO

    public boolean isStarted() {
        return startTimestamp != null;
    }

    public void startFrom(Long actionTimestamp) {
        startTimestamp = actionTimestamp;
    }

    public boolean hasDuration() {
        return duration != null;
    }
}
