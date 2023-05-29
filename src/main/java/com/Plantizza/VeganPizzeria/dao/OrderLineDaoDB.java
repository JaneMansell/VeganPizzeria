package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Order;
import com.Plantizza.VeganPizzeria.entities.OrderLine;
import com.Plantizza.VeganPizzeria.entities.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class OrderLineDaoDB implements OrderLineDao {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    PizzaDaoDB pizzaDao;

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
    public List<OrderLine> getOrderLinesByCustomerId(int customerId) {
        final String SELECT_ORDER_LINES_BY_CUSTOMER_ID = "SELECT ol.* " +
                "FROM orderLines ol " +
                "INNER JOIN orders o ON ol.orderId = o.orderId " +
                "WHERE o.customerId = ?";
        List<OrderLine> orderLines = jdbc.query(SELECT_ORDER_LINES_BY_CUSTOMER_ID, new OrderLineMapper(), customerId);
        return orderLines;
    }

    @Override
    public OrderLine addOrderLine(OrderLine orderLine, int orderId) {
        final String INSERT_ORDER_LINE = "INSERT INTO orderLines(orderId, pizzaName, quantity, lineCost) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORDER_LINE,
                orderLine.getOrderId(),
                orderLine.getPizzaName(),
                orderLine.getQuantity(),
                orderLine.getLineCost());

        int newLineOrderId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        orderLine.setLineOrderId(newLineOrderId);
        orderLine.setOrderId(orderId);

        return orderLine;
    }

    public BigDecimal getCostOfOrderLine(Integer lineOrderId) {
        final String GET_TOTAL_COST = "SELECT p.pizzaPrice * ol.quantity " +
                "FROM pizzas p " +
                "INNER JOIN orderLines ol ON p.pizzaName = ol.pizzaName " +
                "WHERE ol.lineOrderId = ?";

        try {
            return jdbc.queryForObject(GET_TOTAL_COST, new Object[]{lineOrderId}, BigDecimal.class);
        } catch (EmptyResultDataAccessException e) {
            return null; // or handle the exception accordingly
        }
    }

    @Override
    public void updateOrderLine(OrderLine orderLine) {
        // only quantity can be updated. User can just delete orderLine if changing pizza.
        // If quantity updated to 0, toggle deleteOrderLine. (service layer??)
    final String UPDATE_ORDER_LINE = "UPDATE orderLines SET quantity WHERE lineOrderId = ?";
    jdbc.update(UPDATE_ORDER_LINE, new OrderLineMapper(), orderLine.getLineOrderId());
    }

    @Override
    public void deleteOrderLine(int lineOrderId) {
    final String DELETE_ORDER_LINE = "DELETE FROM orderLines WHERE lineOrderId = ?";
    jdbc.update(DELETE_ORDER_LINE, lineOrderId);
    }

    public static final class OrderLineMapper implements RowMapper<OrderLine> {
        @Override
        public OrderLine mapRow(ResultSet rs, int index) throws SQLException {
            OrderLine orderLine = new OrderLine();
            orderLine.setLineOrderId(rs.getInt("lineOrderId"));
            orderLine.setOrderId(rs.getInt("orderId"));
            orderLine.setPizzaName(rs.getString("pizzaName"));
            orderLine.setQuantity(rs.getInt("quantity"));
            orderLine.setLineCost(rs.getBigDecimal("lineCost"));
            return orderLine;
        }
    }
}
