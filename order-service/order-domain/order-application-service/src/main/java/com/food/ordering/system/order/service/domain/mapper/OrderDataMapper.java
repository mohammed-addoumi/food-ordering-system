package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.domain.valueobjects.CustomerId;
import com.food.ordering.system.domain.valueobjects.Money;
import com.food.ordering.system.domain.valueobjects.ProductId;
import com.food.ordering.system.domain.valueobjects.RestaurantId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.ordering.food.system.order.service.domain.entity.Order;
import com.ordering.food.system.order.service.domain.entity.OrderItem;
import com.ordering.food.system.order.service.domain.entity.Product;
import com.ordering.food.system.order.service.domain.entity.Restaurant;
import com.ordering.food.system.order.service.domain.valueobjects.StreetAdress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Restaurant orderCreateCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        List<Product> productList = createOrderCommand.getOrderItems().stream()
                                    .map(orderItem -> new Product(new ProductId(orderItem.getProductId())))
                                    .collect(Collectors.toList());
        return new Restaurant(new RestaurantId(createOrderCommand.getRestaurantId()),
                                productList);
    }

    public Order orderCreateCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAdress(orderAdressToStreetAdress(createOrderCommand.getOrderAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .orderItems(orderItemsToOrderItemsEntitties(createOrderCommand.getOrderItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemsEntitties(
            List<com.food.ordering.system.order.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream().map(orderItem -> {
            return OrderItem.builder()
                    .product(new Product(new ProductId(orderItem.getProductId())))
                    .price(new Money(orderItem.getPrice()))
                    .quantity(orderItem.getQuantity())
                    .subTotal(new Money(orderItem.getSubTotal()))
                    .build();
        }).collect(Collectors.toList());
    }

    private StreetAdress orderAdressToStreetAdress(OrderAddress orderAddress) {
        return new StreetAdress(UUID.randomUUID(),
                            orderAddress.getStreet(),
                            orderAddress.getCodePostal(),
                            orderAddress.getCity());
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order,String message) {
        return CreateOrderResponse.builder()
                .trackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }
}
