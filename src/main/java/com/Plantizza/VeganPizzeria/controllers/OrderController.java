package com.Plantizza.VeganPizzeria.controllers;

import com.Plantizza.VeganPizzeria.dao.CustomerDao;
import com.Plantizza.VeganPizzeria.dao.OrderDao;
import com.Plantizza.VeganPizzeria.dao.OrderLineDao;
import com.Plantizza.VeganPizzeria.entities.Order;
import com.Plantizza.VeganPizzeria.entities.OrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderDao orderDao;
    private int customerId =4; //To be removed when login functionality is added.

    @Autowired
    CustomerDao customerDao;

    @Autowired
    OrderLineDao orderLineDao;

    @GetMapping("customerTrackOrder")
    public String displayStatusToCustomer(Model model) {

        //LocalDate today = LocalDate.now(); Add when there is an order for today
        customerId = 3; //Remove when login complete
        LocalDate today = LocalDate.parse("2023-05-26"); //Remove when in production

        List<Order> orders = orderDao.getAllOrdersByCustomerIdByDate(customerId,today);
        model.addAttribute("orders", orders);


        return "customerTrackOrder";
    }

    @GetMapping("cookTrackOrder")
    public String displayStatusToCook (Model model) {
        //LocalDate today = LocalDate.now(); Add when there is an order for today
        LocalDate today = LocalDate.parse("2023-05-26"); //Remove when in production
        List<Order> orders = orderDao.getAllOrdersByDateForCook(today);
        List<OrderLine> orderLines = orderLineDao.getAllOrderLines();
        model.addAttribute("orders", orders);
        model.addAttribute("orderLines", orderLines);

        return "cookTrackOrder";
    }

}
