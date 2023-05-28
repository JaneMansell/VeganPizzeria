package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Order;

import java.util.List;

public interface OrderDao {
    Order getOrderByID(int id);
    List<Order> getAllOrders();
    Order addOrder(Order order);
    void updateOrder(Order order);
    void deleteOrderById(int id);
}
