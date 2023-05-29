package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    Order getOrderById(int id);
    List<Order> getAllOrders();
    List<Order> getAllOrdersByCustomerIdByDate(int cid, LocalDate date);
    Order createBlankOrder(int customerId);
    Order addOrder(Order order);
    void updateOrder(Order order);
    BigDecimal calculateOrderTotal(int id);
    void deleteOrderById(int id);
}
