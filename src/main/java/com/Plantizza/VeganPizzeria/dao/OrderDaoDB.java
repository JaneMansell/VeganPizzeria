package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class OrderDaoDB implements OrderDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Order getOrderByID(int id) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public Order addOrder(Order order) {
        return null;
    }

    @Override
    public void updateOrder(Order order) {

    }

    @Override
    public void deleteOrderById(int id) {

    }

    public static final class OrderMapper implements RowMapper<Order>{

        @Override
        public Order mapRow(ResultSet rs, int index) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("orderId"));
            order.setCustomerId(rs.getInt("customerId"));
            order.setOrderPlacedTime(LocalTime.parse((CharSequence) rs.getTime("orderPlacedTime")));
            order.setOrderDate(LocalDate.parse((CharSequence) rs.getDate("orderDate")));
            order.setTotal(rs.getBigDecimal("total"));
            order.setOrderStatus(rs.getString("orderStatus"));

            return order;
        }
    }
}
