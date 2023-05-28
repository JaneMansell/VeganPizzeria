package com.Plantizza.VeganPizzeria.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Order {
    private int id;
    private int customerId;
    private LocalTime orderPlacedTime;
    private LocalDate orderDate;
    private BigDecimal total;
    private String orderStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalTime getOrderPlacedTime() {
        return orderPlacedTime;
    }

    public void setOrderPlacedTime(LocalTime orderPlacedTime) {
        this.orderPlacedTime = orderPlacedTime;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && customerId == order.customerId && Objects.equals(orderPlacedTime, order.orderPlacedTime) && Objects.equals(orderDate, order.orderDate) && Objects.equals(total, order.total) && Objects.equals(orderStatus, order.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, orderPlacedTime, orderDate, total, orderStatus);
    }
}
