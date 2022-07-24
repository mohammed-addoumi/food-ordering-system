package com.food.ordering.system.order.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
public class OrderAddress {

    @NotNull
    @Max(50)
    private final String street;
    @NotNull
    @Max(6)
    private final String codePostal;
    @NotNull
    @Max(50)
    private final String city;
}
