package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.OrderLine;

import java.math.BigDecimal;
import java.util.List;

public interface OrderLineDao {

    OrderLine getOrderLineByLineOrderId(int lineOrderId);

    List<OrderLine> getOrderLinesByOrderId(int orderId);

    List<OrderLine> getOrderLinesByCustomerId(int customerId);
    List<OrderLine> getAllOrderLines();

    OrderLine addOrderLine(OrderLine orderLine);

    void updateOrderLine(OrderLine orderLine);

    void deleteOrderLine(int lineOrderId);
}
