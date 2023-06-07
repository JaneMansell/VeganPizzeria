package com.Plantizza.VeganPizzeria.controllers;

import com.Plantizza.VeganPizzeria.dao.PizzaDao;
import com.Plantizza.VeganPizzeria.entities.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    PizzaDao pizzaDao;

    @GetMapping("menu")
    public String displayMenu(Model model) {
        /* Method to display menu of available pizzas */
        List<Pizza> pizzas = pizzaDao.getAllPizzas();

        model.addAttribute("pizzas", pizzas);
        return "menu";
    }


}
