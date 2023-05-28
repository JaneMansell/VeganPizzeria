package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.OrderLine;
import com.Plantizza.VeganPizzeria.entities.Pizza;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class OrderLineDaoDB implements OrderLineDao {

    public static final class OrderLineMapper implements RowMapper<OrderLine> {
        @Override
        public OrderLine mapRow(ResultSet rs, int index) throws SQLException {
            OrderLine orderLine = new OrderLine();
            orderLine.setLineOrderId(rs.getInt("lineOrderId"));
            orderLine.setOrderId(rs.getInt("orderId"));
            orderLine.setPizzaName(rs.getString("pizzaName"));
            orderLine.setQuantity(rs.getInt("quantity"));

            return orderLine;
        }
    }
}
