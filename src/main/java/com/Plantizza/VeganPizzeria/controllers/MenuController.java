package com.Plantizza.VeganPizzeria.controllers;

import com.Plantizza.VeganPizzeria.dao.PizzaDao;
import com.Plantizza.VeganPizzeria.entities.Order;
import com.Plantizza.VeganPizzeria.entities.OrderLine;
import com.Plantizza.VeganPizzeria.entities.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public class MenuController {

    @Autowired
    PizzaDao pizzaDao;

    @GetMapping("menu")
    public String displayMenu(Model model) {
        List<Pizza> pizzas = pizzaDao.getAllPizzas();

        model.addAttribute("pizzas", pizzas);
        return "menu";
    }


}
