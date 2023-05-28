package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.OrderLine;
import com.Plantizza.VeganPizzeria.entities.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class OrderLineDaoDB implements OrderLineDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public OrderLine getOrderLineByLineOrderId(int lineOrderId) {
        try {
            final String SELECT_ORDER_LINE_BY_LO_ID = "SELECT * FROM orderLines WHERE lineOrderId = ?";
            return jdbc.queryForObject(SELECT_ORDER_LINE_BY_LO_ID, new OrderLineMapper(), lineOrderId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public OrderLine getOrderLineByOrderId(int orderId) {
        try {
            final String SELECT_ORDER_LINE_BY_ORDER_ID = "SELECT * FROM orderLines WHERE orderId = ?";
            return jdbc.queryForObject(SELECT_ORDER_LINE_BY_ORDER_ID, new OrderLineMapper(), orderId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public OrderLine addOrderLine(OrderLine orderLine) {
        final String INSERT_ORDER_LINE = "INSERT INTO orderLines(pizzaName, quantity) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORDER_LINE,
                orderLine.getLineOrderId(),
                orderLine.getOrderId(),
                orderLine.getPizzaName(),
                orderLine.getQuantity());

        // need to think about setting orderID correctly... grab max value from orders table?
        // But need to make sure that one order "transaction" has same orderId.
        /*int newLineOrderId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        int newOrderId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        orderLine.setLineOrderId(newLineOrderId);
        orderLine.setOrderId(newOrderId);*/

        return orderLine;
    }

    @Override
    public void updateOrderLine(OrderLine orderLine) {
        // only quantity can be updated. User can just delete orderLine if changing pizza.
        // If quantity updated to 0, toggle deleteOrderLine. (service layer??)
    final String UPDATE_ORDER_LINE = "UPDATE orderLines SET quantity WHERE lineOrderId = ?";
    jdbc.update(UPDATE_ORDER_LINE, new OrderLineMapper(), orderLine.getLineOrderId());
    }

    @Override
    public void deleteOrderLineByLineOrderId(int lineOrderId) {
    final String DELETE_ORDER_LINE = "DELETE FROM orderLines WHERE lineOrderId = ?";
    jdbc.update(DELETE_ORDER_LINE, new OrderLineMapper(), lineOrderId);
    }

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
