package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Customer;
import com.Plantizza.VeganPizzeria.entities.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CustomerDaoDB implements CustomerDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Customer getCustomerById(int customerId) {
        try {
            final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE customerId = ?";
            return jdbc.queryForObject(SELECT_CUSTOMER_BY_ID, new CustomerMapper(), customerId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Customer addCustomer(Customer customer) {
        final String INSERT_CUSTOMER = "INSERT INTO customers(customerName, customerEmailAddress, " +
                "customerFirstLineAddress, customerPostCode, customerPhone) " +
                "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_CUSTOMER,
                customer.getCustomerName(),
                customer.getCustomerEmailAddress(),
                customer.getCustomerFirstLineAddress(),
                customer.getCustomerPostCode(),
                customer.getCustomerPhone());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        customer.setCustomerId(newId);
        return customer;
    }

    public static final class CustomerMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int index) throws SQLException {
            Customer customer = new Customer();
            customer.setCustomerId(rs.getInt("customerId"));
            customer.setCustomerName(rs.getString("customerName"));
            customer.setCustomerEmailAddress(rs.getString("customerEmailAddress"));
            customer.setCustomerFirstLineAddress(rs.getString("customerFirstLineAddress"));
            customer.setCustomerPostCode(rs.getString("customerPostCode"));
            customer.setCustomerPhone(rs.getString("customerPhone"));

            return customer;
        }
    }
}
