package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDaoDB implements OrderDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Order getOrderById(int id) {
        try{
            final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE orderId = ?";
            return jdbc.queryForObject(GET_ORDER_BY_ID, new OrderMapper(), id);
        } catch(DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        final String GET_ALL_ORDERS = "SELECT * FROM orders";
        return jdbc.query(GET_ALL_ORDERS, new OrderMapper());
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate date){
        final String GET_ALL_ORDERS_BY_DATE = "SELECT * " +
                "FROM orders WHERE orderDate = ? " +
                "ORDER BY orderPlacedTime";
        return jdbc.query(GET_ALL_ORDERS_BY_DATE, new OrderMapper(), date.toString());
    }

    @Override
    public List<Order> getAllOrdersByDateForCook(LocalDate date){
        final String GET_ALL_ORDERS_BY_DATE_FOR_COOK = "SELECT * " +
                "FROM orders WHERE orderDate = ? AND (orderStatus = ? OR orderStatus =?) " +
                "ORDER BY orderPlacedTime";
        return jdbc.query(GET_ALL_ORDERS_BY_DATE_FOR_COOK, new OrderMapper(), date.toString(), "Cooking", "Ordered");
    }
    @Override
    public List<Order> getAllOrdersByCustomerIdByDate(int cid, LocalDate date){
        final String GET_ALL_ORDERS_BY_CUSTOMER_ID_BY_DATE = "SELECT * " +
                "FROM orders WHERE customerId = ? AND orderDate = ? " +
                "ORDER BY orderDate";
        return jdbc.query(GET_ALL_ORDERS_BY_CUSTOMER_ID_BY_DATE, new OrderMapper(),cid,date.toString());
    }

    @Override
    @Transactional
    public Order addOrder(Order order) {
        final String INSERT_ORDER ="INSERT INTO orders(" +
                "customerId,orderPlacedTime, orderDate," +
                "total, orderStatus) VALUES (?,?,?,?,?)";
        jdbc.update(INSERT_ORDER,
                order.getCustomerId(),
                order.getOrderPlacedTime().toString(),
                order.getOrderDate().toString(),
                order.getTotal(),
                order.getOrderStatus());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        order.setId(newId);

        return order;
    }

    @Override
    public void updateOrder(Order order) {
        final String UPDATE_ORDER = "UPDATE orders SET customerId = ?, " +
                "orderPlacedTime =?, orderDate = ?, total =?, orderStatus = ? " +
                "WHERE orderId = ?";
        jdbc.update(UPDATE_ORDER,
                order.getCustomerId(),
                order.getOrderPlacedTime().toString(),
                order.getOrderDate().toString(),
                order.getTotal(),
                order.getOrderStatus(),
                order.getId());
    }

    @Override
    @Transactional
    public void deleteOrderById(int id) {
        //Delete order line items first
        final String DELETE_ORDER_LINES = "DELETE FROM orderLines " +
                "WHERE orderId = ?";
        jdbc.update(DELETE_ORDER_LINES,id);
        //Then delete order
        final String DELETE_ORDER ="DELETE FROM orders WHERE orderId = ?";
        jdbc.update(DELETE_ORDER,id);
    }

    public static final class OrderMapper implements RowMapper<Order>{

        @Override
        public Order mapRow(ResultSet rs, int index) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("orderId"));
            order.setCustomerId(rs.getInt("customerId"));
            order.setOrderPlacedTime((rs.getTime("orderPlacedTime")).toLocalTime());
            order.setOrderDate((rs.getDate("orderDate")).toLocalDate());
            order.setTotal(rs.getBigDecimal("total"));
            order.setOrderStatus(rs.getString("orderStatus"));

            return order;
        }
    }
}
