package com.food.ordering.system.order.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class CreateOrderCommand {

    @NotNull
    private String customerId;
    @NotNull
    private String restaurantId;
    @NotNull
    private BigDecimal price;
    @NotNull
    private List<OrderItem> orderItems;
    @NotNull
    private OrderAddress orderAddress;
}
