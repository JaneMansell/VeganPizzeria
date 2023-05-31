package com.Plantizza.VeganPizzeria.controllers;

import com.Plantizza.VeganPizzeria.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CustomerController {

    @Autowired
    CustomerDao customerDao;

    @GetMapping("customerMenu/{customerID}")
    public String displayCustomerMenu(@PathVariable("customerID") String customerId, Model model ) {
        System.out.println("I am customer " + customerId);

        model.addAttribute("customerId", customerId);
        return "customerMenu";
    }

}
