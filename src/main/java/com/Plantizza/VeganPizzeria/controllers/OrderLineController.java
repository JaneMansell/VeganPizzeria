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
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderLineController {

    @Autowired
    OrderLineDao orderLineDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    PizzaDao pizzaDao;

    @GetMapping("makeOrder")
    public String makeOrder(@RequestParam(name = "id") String id, Model model){
        int customerId = Integer.parseInt(id);
        Order order = createBlankOrder(customerId);  // Create a new blank order
        orderDao.addOrder(order); // add Order
        return "redirect:/placeOrder?id=" +customerId + "&o="+order.getId();
    }

    //Helper method to create a blank order at start of ordering process
    public Order createBlankOrder(int customerId) {
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderPlacedTime(LocalTime.now());
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("Basket");
        order.setTotal(new BigDecimal(0.00));

        return order;
    }

    @GetMapping("placeOrder")
    public String startOrder(@RequestParam(name = "o") String oId, Model model) {
        int orderId = Integer.parseInt(oId);
        Order order = orderDao.getOrderById(orderId);

        List<OrderLine> orderLines = orderLineDao.getOrderLinesByOrderId(order.getId());
        List<Pizza> pizzas = pizzaDao.getAllPizzas();

        // relevant order, orderLines, and pizzas made available to whole controller
        model.addAttribute("order", order);
        model.addAttribute("orderLines", orderLines);
        model.addAttribute("pizzas", pizzas);
        return "placeOrder";
    }

    @PostMapping("/addOrderLine")
    public String addOrderLine(HttpServletRequest request) {
        OrderLine orderLine = new OrderLine();

        //Retrieve form parameters
        String pizzaName = request.getParameter("pizzaName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int customerId = Integer.parseInt(request.getParameter("id"));
        int orderId = Integer.parseInt(request.getParameter("oId"));

        // Calculate cost of orderLine
        BigDecimal pizzaCost = pizzaDao.getPizzaPriceByName(pizzaName);
        BigDecimal lineCost = pizzaCost.multiply(BigDecimal.valueOf(quantity));

        // Set the properties of the OrderLine
        orderLine.setOrderId(orderId);
        orderLine.setPizzaName(pizzaName);
        orderLine.setQuantity(quantity);
        orderLine.setLineCost(lineCost);

        // Add the OrderLine to the database
        orderLineDao.addOrderLine(orderLine);

        // Redirect to the placeOrder page with the same orderId and customerId
        return "redirect:/placeOrder?id=" +customerId + "&o="+orderId;
    }

    @GetMapping("/deleteOrderLine")
    public String deleteOrderLine(Integer lId) {

        //Fetch lineOrder to retrieve orderId and CustomerId
        OrderLine orderLine = orderLineDao.getOrderLineByLineOrderId(lId);
        int orderId = orderLine.getOrderId();
        Order order = orderDao.getOrderById(orderId);
        int customerId = order.getCustomerId();

        //Delete OrderLine
        orderLineDao.deleteOrderLine(lId);
        return "redirect:/placeOrder?id=" +customerId + "&o="+orderId;
    }

    @PostMapping("/submitOrder")
    public String submitOrder(HttpServletRequest request){

        //Retrieve form parameters
        int customerId = Integer.parseInt(request.getParameter("submitCustId"));
        int orderId = Integer.parseInt(request.getParameter("submitOrdId"));
        //Get all the orderLines for the order and find the total
        List<OrderLine> allLinesForOrder = orderLineDao.getOrderLinesByOrderId(orderId);
        List<BigDecimal> bigDecimalList = new ArrayList<>();
        for (OrderLine line : allLinesForOrder){
            bigDecimalList.add(line.getLineCost());
        }
        BigDecimal orderTotal = bigDecimalList.stream()
                .reduce(BigDecimal.ZERO, (p,q) -> p.add(q));

        //Update the order 
        Order order = orderDao.getOrderById(orderId);
        order.setTotal(orderTotal);
        order.setOrderStatus("Ordered");
        order.setOrderPlacedTime(LocalTime.now());
        order.setOrderDate(LocalDate.now());

        orderDao.updateOrder(order);

        return "redirect:/customerTrackOrder?id=" +customerId;
    }
}
