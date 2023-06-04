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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
    CustomerDao customerDao;

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
    public String startOrder(@RequestParam(name = "id") String id, @RequestParam(name = "o") String oId, Model model) {
        int customerId = Integer.parseInt(id);
        int orderId = Integer.parseInt(oId);
        System.out.println("I am customer " + customerId);
        Order order = orderDao.getOrderById(orderId);
        System.out.println("I have retrieved the order");

        List<OrderLine> orderLines = orderLineDao.getOrderLinesByOrderId(order.getId());
        System.out.println("I have the orderlines");
        List<Pizza> pizzas = pizzaDao.getAllPizzas();
        System.out.println("I have the pizzas");

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
        System.out.println("My pizza is " + pizzaName);
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        System.out.println("My quantity is " + quantity);
        int customerId = Integer.parseInt(request.getParameter("id"));
        System.out.println("My customerId is " + customerId);
        int orderId = Integer.parseInt(request.getParameter("oId"));
        System.out.println("My orderId is " + orderId);

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
        System.out.println("I have added a line to the order");

        // Redirect to the placeOrder page with the same orderId and customerId
        return "redirect:/placeOrder?id=" +customerId + "&o="+orderId;
    }

    @GetMapping("/deleteOrderLine")
    public String deleteOrderLine(@RequestParam("id") String id,
                                  @RequestParam("oId") String oId,
                                  @RequestParam("lId") String lId) {
        int customerId = Integer.parseInt(id);
        int orderId = Integer.parseInt(oId);
        int lineOrderId = Integer.parseInt(lId);
        orderLineDao.deleteOrderLine(lineOrderId);
        return "redirect:/placeOrder?id=" +customerId + "&o="+orderId;
    }

    @PostMapping("/submitOrder")
    public String submitOrder(HttpServletRequest request){
        System.out.println("Order submission has been requested");
        //Retrieve form parameters
        int customerId = Integer.parseInt(request.getParameter("submitCustId"));
        System.out.println("My customerId is " + customerId);
        int orderId = Integer.parseInt(request.getParameter("submitOrdId"));
        System.out.println("My orderId is " + orderId);
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
