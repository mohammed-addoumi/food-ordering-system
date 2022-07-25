package com.ordering.food.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobjects.RestaurantId;

import java.util.List;

public class Restaurant extends AggregateRoot<RestaurantId> {

    private final List<Product> productList;
    private boolean active;

    public Restaurant(RestaurantId restaurantId,
                      List<Product> productList) {
        super.setId(restaurantId);
        this.productList = productList;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public boolean isActive() {
        return active;
    }
}
