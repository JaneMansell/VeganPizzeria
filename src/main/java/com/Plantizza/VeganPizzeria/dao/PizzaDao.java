package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Pizza;

import java.math.BigDecimal;
import java.util.List;

public interface PizzaDao {
    Pizza getPizzaByName(String pizzaName);

    public BigDecimal getPizzaPriceByName(String pizzaName);

    List<Pizza> getAllPizzas();

}
