package com.ordering.food.system.order.service.domain.valueobjects;

import com.food.ordering.system.domain.valueobjects.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
