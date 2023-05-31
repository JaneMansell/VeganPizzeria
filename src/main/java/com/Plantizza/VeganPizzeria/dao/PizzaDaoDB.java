package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PizzaDaoDB implements PizzaDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Pizza getPizzaByName(String pizzaName) {
        final String GET_PIZZA_BY_NAME = "SELECT * FROM pizzas WHERE pizzaName = ?";
        return jdbc.queryForObject(GET_PIZZA_BY_NAME, new PizzaMapper(), pizzaName);
    }

    @Override
    public BigDecimal getPizzaPriceByName(String pizzaName) {
        final String GET_PIZZA_PRICE_BY_NAME = "SELECT * FROM pizzas WHERE pizzaName = ?";
        return jdbc.queryForObject(GET_PIZZA_PRICE_BY_NAME, new PizzaMapper(), pizzaName).getPizzaPrice();
    }

    @Override
    public List<Pizza> getAllPizzas() {
        final String GET_ALL_PIZZAS = "SELECT * FROM pizzas";
        return jdbc.query(GET_ALL_PIZZAS, new PizzaMapper());
    }

    public static final class PizzaMapper implements RowMapper<Pizza> {
        @Override
        public Pizza mapRow(ResultSet rs, int index) throws SQLException {
            Pizza pizza = new Pizza();
            pizza.setPizzaName(rs.getString("pizzaName"));
            pizza.setPizzaPrice(rs.getBigDecimal("pizzaPrice"));
            pizza.setPizzaDesc(rs.getString("pizzaDesc"));
            pizza.setPizzaImgURL(rs.getString("pizzaImgURL"));

            return pizza;
        }
    }
}
