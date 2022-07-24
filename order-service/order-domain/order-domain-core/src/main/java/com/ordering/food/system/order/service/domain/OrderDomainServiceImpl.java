package com.ordering.food.system.order.service.domain;

import com.ordering.food.system.order.service.domain.entity.Order;
import com.ordering.food.system.order.service.domain.entity.Product;
import com.ordering.food.system.order.service.domain.entity.Restaurant;
import com.ordering.food.system.order.service.domain.events.OrderCancelledEvent;
import com.ordering.food.system.order.service.domain.events.OrderCreatedEvent;
import com.ordering.food.system.order.service.domain.events.OrderPaidEvent;
import com.ordering.food.system.order.service.domain.exceptions.OrderDomainException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class OrderDomainServiceImpl implements OrderDomainService {

    public static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order,restaurant);
        order.validateOrder();
        order.initializeOrder();
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getOrderItems().stream().forEach(orderItem ->
                                                restaurant.getProductList().forEach(product -> {
                                                    Product currentProduct = orderItem.getProduct();
                                                    if(product.equals(currentProduct)){
                                                        currentProduct.updateWithConfirmedNameAndPrice(product.getName(),
                                                                                                        product.getPrice());
                                                    }
        }));
    }

    private void validateRestaurant(Restaurant restaurant) {
        if(!restaurant.isActive()){
            throw new OrderDomainException("restaurant " + restaurant.getId().toString() + " is not active");
        }
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        return new OrderPaidEvent(order,ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        return new OrderCancelledEvent(order,ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);

    }
}
