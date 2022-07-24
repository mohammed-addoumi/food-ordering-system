package com.ordering.food.system.order.service.domain.entity;

import com.food.ordering.system.domain.valueobjects.BaseId;
import com.food.ordering.system.domain.valueobjects.Money;
import com.food.ordering.system.domain.valueobjects.ProductId;

public class Product extends BaseId<ProductId> {
    private String name;
    private Money price;



    public Product(ProductId value) {
        super(value);
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public void updateWithConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
