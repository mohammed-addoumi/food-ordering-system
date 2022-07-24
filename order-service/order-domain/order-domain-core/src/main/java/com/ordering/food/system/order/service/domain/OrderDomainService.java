package com.ordering.food.system.order.service.domain;

import com.ordering.food.system.order.service.domain.entity.Order;
import com.ordering.food.system.order.service.domain.entity.Restaurant;
import com.ordering.food.system.order.service.domain.events.OrderCancelledEvent;
import com.ordering.food.system.order.service.domain.events.OrderCreatedEvent;
import com.ordering.food.system.order.service.domain.events.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order,List<String> failureMessages);
}
