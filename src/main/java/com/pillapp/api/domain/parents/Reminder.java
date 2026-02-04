package com.pillapp.api.domain.parents;

import com.pillapp.api.domain.entities.Patient;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
abstract public class Reminder extends FeedItem {

    @ManyToOne
    public Patient patient;

    public Long reminderTimestamp = 0L;

    public boolean isStopped() {
        return reminderTimestamp == null;
    }

    public void stop() {
        reminderTimestamp = null;
    }
}
