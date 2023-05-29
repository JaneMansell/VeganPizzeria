package com.Plantizza.VeganPizzeria.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderLine {

    private int lineOrderId;
    private int orderId;
    private String pizzaName;
    private int quantity;
    private BigDecimal lineCost;

    public int getLineOrderId() { return lineOrderId; }

    public void setLineOrderId(int lineOrderId) { this.lineOrderId = lineOrderId; }

    public int getOrderId() { return orderId; }

    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getPizzaName() { return pizzaName; }

    public void setPizzaName(String pizzaName) { this.pizzaName = pizzaName; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getLineCost() { return lineCost; }

    public void setLineCost(BigDecimal lineCost) { this.lineCost = lineCost; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLine orderLine)) return false;
        return lineOrderId == orderLine.lineOrderId && orderId == orderLine.orderId && quantity == orderLine.quantity && Objects.equals(pizzaName, orderLine.pizzaName) && Objects.equals(lineCost, orderLine.lineCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineOrderId, orderId, pizzaName, quantity, lineCost);
    }
}
