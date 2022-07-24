package com.ordering.food.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobjects.*;
import com.ordering.food.system.order.service.domain.exceptions.OrderDomainException;
import com.ordering.food.system.order.service.domain.valueobjects.OrderItemId;
import com.ordering.food.system.order.service.domain.valueobjects.StreetAdress;
import com.ordering.food.system.order.service.domain.valueobjects.TrackingId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Order extends AggregateRoot<OrderId> {

    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAdress deliveryAdress;
    private Money price;
    private List<OrderItem> orderItems;
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    private Order(Builder builder) {
        super.setId(builder.id);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAdress = builder.deliveryAdress;
        price = builder.price;
        orderItems = builder.orderItems;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAdress getDeliveryAdress() {
        return deliveryAdress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static Builder newBuilder(CustomerId customerId, RestaurantId restaurantId, StreetAdress deliveryAdress) {
        return new Builder(customerId, restaurantId, deliveryAdress);
    }

    public void initializeOrder(){
        setId(new OrderId(UUID.randomUUID()));
        orderStatus = OrderStatus.PENDING;
        trackingId = new TrackingId(UUID.randomUUID());
        initializeOrderItems();
    }

    public void validateOrder(){
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    public void pay(){
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("order not in the correct status to pay operation");
        }
        orderStatus = OrderStatus.PAID;
    }

    public void approve(){
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("order not in the correct status to APPROVE operation");
        }
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages){
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("order not in the correct status to init cancel operation");
        }
        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }


    public void cancel(List<String> failureMessages){
        if (orderStatus != OrderStatus.PENDING && orderStatus != OrderStatus.CANCELLING) {
            throw new OrderDomainException("order not in the correct status to cancel operation");
        }
        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if(this.failureMessages != null && failureMessages != null){
            this.failureMessages.addAll(failureMessages.stream().filter(String::isEmpty).collect(toList()));
        }
        if(this.failureMessages == null){
            this.failureMessages = failureMessages;
        }

    }

    private void validateItemsPrice() {
        Money totalPrice = orderItems.stream().map(orderItem -> {
            validateOrderItem(orderItem);
            return orderItem.getPrice();
        }).reduce(Money.ZERO, Money::add);

        if(!totalPrice.equals(price)){
            throw new OrderDomainException("price " + price.getAmount() +
                                            " is not equal to total price " + totalPrice.getAmount());
        }

    }

    private void validateOrderItem(OrderItem orderItem) {
        if(!orderItem.isPriceValid()){
            throw new OrderDomainException("order item price " + orderItem.getPrice().getAmount() +
                                            " is not valid for product " + orderItem.getProduct().getName());
        }
    }

    private void validateTotalPrice() {
        if (price == null || price.isGreaterThanZero()){
            throw new OrderDomainException("price should be greater than zero");
        }
    }

    private void validateInitialOrder() {
        if(orderStatus != null || getId() != null){
            throw new OrderDomainException("order is not in the correct status for initialization");
        }
    }

    private void initializeOrderItems() {
        long itemId = 1;
        for(OrderItem orderItem : orderItems){
            orderItem.initializeOrderItem(super.getId(),new OrderItemId(itemId++));
        }
    }

    public static final class Builder {
        private OrderId id;
        private final CustomerId customerId;
        private final RestaurantId restaurantId;
        private final StreetAdress deliveryAdress;
        private Money price;
        private List<OrderItem> orderItems;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder(CustomerId customerId, RestaurantId restaurantId, StreetAdress deliveryAdress) {
            this.customerId = customerId;
            this.restaurantId = restaurantId;
            this.deliveryAdress = deliveryAdress;
        }

        public Builder id(OrderId val) {
            id = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder orderItems(List<OrderItem> val) {
            orderItems = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }


}
