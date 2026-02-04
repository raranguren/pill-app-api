package com.pillapp.api.dto.updates;

import java.util.List;

public class UpdatesFeedDTO {

    public Long lastUpdate;
    public String next;
    public List<FeedItemDTO> updates;

    public UpdatesFeedDTO(List<FeedItemDTO> updates, long fromTimestamp) {
        this.updates = updates;
        this.lastUpdate = updates.stream()
                .mapToLong(i->i.lastUpdate)
                .max()
                .orElse(fromTimestamp);
        this.next="/?t=" + lastUpdate;
    }
}
