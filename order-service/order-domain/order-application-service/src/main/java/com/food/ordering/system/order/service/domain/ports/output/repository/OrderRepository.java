package com.food.ordering.system.order.service.domain.ports.output.repository;

import com.ordering.food.system.order.service.domain.entity.Order;
import com.ordering.food.system.order.service.domain.valueobjects.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findOrderByTrackingID(TrackingId trackingId);
}
