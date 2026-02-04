package com.pillapp.api.dto.updates;

import com.pillapp.api.domain.parents.FeedItem;

public class FeedItemDTO implements Comparable<FeedItemDTO> {

    public String type;
    public Long lastUpdate;
    public Object data;

    public FeedItemDTO(FeedItem feedItem) {
        this.lastUpdate = feedItem.updatedTimestamp;
        this.type = feedItem.getType();
        this.data = feedItem.toDTO();
    }

    @Override
    public int compareTo(FeedItemDTO other) {
        return (int) (other.lastUpdate - lastUpdate);
    }
}
