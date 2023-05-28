package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Pizza;

public interface PizzaDao {
    Pizza getPizzaByName(String pizzaName);

}
