package com.ordering.food.system.order.service.domain.exceptions;

import com.food.ordering.system.domain.exceptions.DomainException;

public class OrderNotFoundException extends DomainException {

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
