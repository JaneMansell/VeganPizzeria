package com.Plantizza.VeganPizzeria.controllers;

import com.Plantizza.VeganPizzeria.dao.CustomerDao;
import com.Plantizza.VeganPizzeria.dao.OrderDao;
import com.Plantizza.VeganPizzeria.dao.OrderLineDao;
import com.Plantizza.VeganPizzeria.dao.PizzaDao;
import com.Plantizza.VeganPizzeria.entities.Order;
import com.Plantizza.VeganPizzeria.entities.OrderLine;
import com.Plantizza.VeganPizzeria.entities.Pizza;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class OrderLineController {

    @Autowired
    OrderLineDao orderLineDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    PizzaDao pizzaDao;

    int customerId = 1;

    @GetMapping("placeOrder")
    public String displayOrderLines(Model model) {
        List<OrderLine> orderLines = orderLineDao.getOrderLinesByCustomerId(this.customerId);
        List<Pizza> pizzas = pizzaDao.getAllPizzas();

        model.addAttribute("orderLines", orderLines);
        model.addAttribute("pizzas", pizzas);
        return "placeOrder";
    }

    @PostMapping("/addOrderLine")
    public String addOrderLine(OrderLine orderLine, HttpServletRequest request) {

        // Create the Order
        Order order = new Order();
        order.setCustomerId(this.customerId);
        order.setOrderPlacedTime(LocalTime.now());
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("Basket");
        order.setTotal(new BigDecimal(0.00));

        // Persist the Order
        order = orderDao.addOrder(order);
        int orderId = order.getId();

        // Set the orderId for the OrderLine
        orderLine.setOrderId(orderId);

        // Retrieve other form parameters
        String pizzaName = request.getParameter("pizzaName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // Calculate cost of orderLine
        BigDecimal pizzaCost = pizzaDao.getPizzaPriceByName(pizzaName);
        BigDecimal lineCost = pizzaCost.multiply(BigDecimal.valueOf(quantity));

        // Set the remaining properties of the OrderLine
        orderLine.setPizzaName(pizzaName);
        orderLine.setQuantity(quantity);
        orderLine.setLineCost(lineCost);

        // Add the OrderLine to the database
        orderLineDao.addOrderLine(orderLine, orderId);

        return "redirect:/placeOrder";
    }

    @GetMapping("/deleteOrderLine")
    public String deleteOrderLine(@RequestParam("id") Integer lineOrderId) {
        orderLineDao.deleteOrderLine(lineOrderId);
        return "redirect:/placeOrder";
    }

}
