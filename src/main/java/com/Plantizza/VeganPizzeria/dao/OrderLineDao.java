package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.OrderLine;

import java.util.List;

public interface OrderLineDao {

    OrderLine getOrderLineByLineOrderId(int lineOrderId);

    OrderLine getOrderLineByOrderId(int orderId);

    List<OrderLine> getOrderLinesByCustomerId(int customerId);

    OrderLine addOrderLine(OrderLine orderLine, int orderId);

    void updateOrderLine(OrderLine orderLine);

    void deleteOrderLine(int lineOrderId);
}
