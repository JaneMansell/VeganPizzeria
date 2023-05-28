package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.OrderLine;

public interface OrderLineDao {

    OrderLine getOrderLineByLineOrderId(int lineOrderId);

    OrderLine getOrderLineByOrderId(int orderId);

    OrderLine addOrderLine(OrderLine orderLine);

    void updateOrderLine(OrderLine orderLine);

    void deleteOrderLineByLineOrderId(int lineOrderId);
}
