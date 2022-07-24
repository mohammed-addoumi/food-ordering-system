package com.ordering.food.system.order.service.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class StreetAdress {
    private final UUID id;
    private final String string;
    private final String codePostal;
    private final String city;

    public StreetAdress(UUID id, String string, String codePostal, String city) {
        this.id = id;
        this.string = string;
        this.codePostal = codePostal;
        this.city = city;
    }

    public UUID getId() {
        return id;
    }

    public String getString() {
        return string;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetAdress that = (StreetAdress) o;
        return Objects.equals(string, that.string) && Objects.equals(codePostal, that.codePostal) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string, codePostal, city);
    }
}
