package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.OrderLine;

import java.util.List;

public interface OrderLineDao {

    OrderLine getOrderLineByLineOrderId(int lineOrderId);

    OrderLine getOrderLineByOrderId(int orderId);

    List<OrderLine> getAllOrderLines();

    OrderLine addOrderLine(OrderLine orderLine);

    void updateOrderLine(OrderLine orderLine);

    void deleteOrderLineByLineOrderId(int lineOrderId);
}
