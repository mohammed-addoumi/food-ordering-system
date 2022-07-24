package com.ordering.food.system.order.service.domain.valueobjects;

import com.food.ordering.system.domain.valueobjects.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
