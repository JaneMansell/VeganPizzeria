package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustomerDao {

    Customer getCustomerById(int customerId);

    Customer getCustomerByEmail(String email);

    Customer addCustomer(Customer customer);
}
