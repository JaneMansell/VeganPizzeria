package com.Plantizza.VeganPizzeria.entities;

import java.util.Objects;

public class OrderLine {

    private int lineOrderId;
    private int orderId;
    private String pizzaName;
    private int quantity;

    public int getLineOrderId() { return lineOrderId; }

    public void setLineOrderId(int lineOrderId) { this.lineOrderId = lineOrderId; }

    public int getOrderId() { return orderId; }

    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getPizzaName() { return pizzaName; }

    public void setPizzaName(String pizzaName) { this.pizzaName = pizzaName; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLine that)) return false;
        return lineOrderId == that.lineOrderId && orderId == that.orderId && quantity == that.quantity && Objects.equals(pizzaName, that.pizzaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineOrderId, orderId, pizzaName, quantity);
    }
}
