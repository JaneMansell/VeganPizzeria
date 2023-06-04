package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    Order getOrderById(int id);
    List<Order> getAllOrders();
    List<Order> getAllOrdersByDate(LocalDate date);
    List<Order> getAllOrdersByDateForCook(LocalDate date);
    List<Order> getAllOrdersByCustomerIdByDate(int cid, LocalDate date);
    Order addOrder(Order order);
    void updateOrder(Order order);
    void deleteOrderById(int id);
}
