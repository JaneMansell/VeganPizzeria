package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PizzaDaoDB implements PizzaDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Pizza getPizzaByName(String pizzaName) {
        final String GET_PIZZA_BY_NAME = "SELECT * FROM pizzas WHERE pizzaName = ?";
        return jdbc.queryForObject(GET_PIZZA_BY_NAME, new PizzaMapper(), pizzaName);
    }

    public static final class PizzaMapper implements RowMapper<Pizza> {
        @Override
        public Pizza mapRow(ResultSet rs, int index) throws SQLException {
            Pizza pizza = new Pizza();
            pizza.setPizzaName(rs.getString("pizzaName"));
            pizza.setPizzaPrice(rs.getBigDecimal("pizzaPrice"));

            return pizza;
        }
    }
}
