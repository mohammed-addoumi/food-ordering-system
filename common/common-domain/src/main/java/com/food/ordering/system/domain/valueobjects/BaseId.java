package com.food.ordering.system.domain.valueobjects;

public abstract class BaseId<T> {
    private final T value;

    protected BaseId(T value) {
        this.value = value;
    }
}
