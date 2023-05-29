package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Pizza;

import java.util.List;

public interface PizzaDao {
    Pizza getPizzaByName(String pizzaName);

    List<Pizza> getAllPizzas();

}
