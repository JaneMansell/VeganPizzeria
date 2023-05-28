package com.Plantizza.VeganPizzeria.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Pizzas {
    private String pizzaName;
    private BigDecimal pizzaPrice;

    public String getPizzaName() { return pizzaName; }

    public void setPizzaName(String pizzaName) { this.pizzaName = pizzaName; }

    public BigDecimal getPizzaPrice() { return pizzaPrice; }

    public void setPizzaPrice(BigDecimal pizzaPrice) { this.pizzaPrice = pizzaPrice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizzas pizzas)) return false;
        return Objects.equals(pizzaName, pizzas.pizzaName) && Objects.equals(pizzaPrice, pizzas.pizzaPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pizzaName, pizzaPrice);
    }
}
