package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Customer;
import com.Plantizza.VeganPizzeria.entities.Pizza;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CustomerDaoDB implements CustomerDao{

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
