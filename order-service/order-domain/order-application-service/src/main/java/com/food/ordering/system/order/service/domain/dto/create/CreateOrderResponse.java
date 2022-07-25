package com.food.ordering.system.order.service.domain.dto.create;

import com.food.ordering.system.domain.valueobjects.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class CreateOrderResponse {
    @NotNull
    private UUID trackingId;
    @NotNull
    private OrderStatus orderStatus;
    @NotNull
    private String message;
}
