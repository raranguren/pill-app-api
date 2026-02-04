package com.pillapp.api.domain.parents;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public abstract class FeedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long updatedTimestamp = Instant.now().getEpochSecond();

    public abstract String getType();
    public abstract Object toDTO();

}
