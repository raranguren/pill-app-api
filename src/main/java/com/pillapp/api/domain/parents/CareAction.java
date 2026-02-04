package com.pillapp.api.domain.parents;

import com.pillapp.api.domain.entities.Patient;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
public abstract class CareAction extends FeedItem {

    @ManyToOne
    public Patient patient;

    public Long doneTimestamp;

}
