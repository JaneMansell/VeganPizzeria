package com.Plantizza.VeganPizzeria.controllers;

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
    public String makeOrder(@RequestParam(name = "id") String id, Model model) {
        /* Method to create a new order at the start of ordering process.
        *  Method is called when Place Order button is pressed on customer
        *  menu.
        *  The id of the order is passed to the place order page and
        *  that page is then displayed. */
        int customerId = Integer.parseInt(id);
        Order order = createBlankOrder(customerId);  // Create a new blank order
        orderDao.addOrder(order); // add Order
        return "redirect:/placeOrder?id=" + customerId + "&o=" + order.getId();
    }


    public Order createBlankOrder(int customerId) {
        /* Helper method to create a blank order at start of ordering
         * process */
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
        /* Method that displays the placeOrder.html page. */
        int orderId = Integer.parseInt(oId);
        Order order = orderDao.getOrderById(orderId);

        List<OrderLine> orderLines = orderLineDao.getOrderLinesByOrderId(order.getId());
        List<Pizza> pizzas = pizzaDao.getAllPizzas();

        // relevant order, orderLines, and pizzas made available to the whole controller
        model.addAttribute("order", order);
        model.addAttribute("orderLines", orderLines);
        model.addAttribute("pizzas", pizzas);
        return "placeOrder";
    }

    @PostMapping("/addOrderLine")
    public String addOrderLine(HttpServletRequest request) {
        /* Method that adds a line to the order and the orderLines database
        *  when the Add Order button is clicked. */
        OrderLine orderLine = new OrderLine();

        // Retrieve form parameters
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

        // Update the Order object
        Order order = orderDao.getOrderById(orderId);
        updateOrderTotal(order);

        // Redirect to the placeOrder page with the same orderId and customerId
        return "redirect:/placeOrder?id=" + customerId + "&o=" + orderId;
    }

    @GetMapping("/deleteOrderLine")
    public String deleteOrderLine(Integer lId) {
        /* Method that deletes a line from the order on the placeOrder page
         * and deletes the orderLine from the orderLines database */

        // Fetch lineOrder to retrieve orderId and CustomerId
        OrderLine orderLine = orderLineDao.getOrderLineByLineOrderId(lId);
        int orderId = orderLine.getOrderId();
        Order order = orderDao.getOrderById(orderId);
        int customerId = order.getCustomerId();

        // Delete OrderLine
        orderLineDao.deleteOrderLine(lId);

        // Update the Order object
        updateOrderTotal(order);

        return "redirect:/placeOrder?id=" + customerId + "&o=" + orderId;
    }

    @PostMapping("/submitOrder")
    public String submitOrder(HttpServletRequest request) {

        /* Method that update the order created in the makeOrder mapping
        *  with the orderLines added to the order.
        *  It is activated by the Submit Order button on the placeOrder.html page */

        // Retrieve form parameters
        int customerId = Integer.parseInt(request.getParameter("submitCustId"));
        int orderId = Integer.parseInt(request.getParameter("submitOrdId"));

        // Get the order, retrieve the order lines and total them
        // and update the order in the database.
        Order order = orderDao.getOrderById(orderId);
        updateOrderTotal(order);

        // Update the Order object with date and time
        order.setOrderPlacedTime(LocalTime.now());
        order.setOrderDate(LocalDate.now());

        //Change the status to Ordered and update the database
        updateOrderStatus(order);

        return "redirect:/customerTrackOrder?id=" + customerId;
    }


    private void updateOrderTotal(Order order) {
        // Helper method to update the total cost of the order
        List<OrderLine> orderLines = orderLineDao.getOrderLinesByOrderId(order.getId());
        BigDecimal orderTotal = BigDecimal.ZERO;
        for (OrderLine line : orderLines) {
            orderTotal = orderTotal.add(line.getLineCost());
        }
        order.setTotal(orderTotal);
        orderDao.updateOrder(order);
    }


    private void updateOrderStatus(Order order) {
        // Helper method to update the status of the order based on order lines
        List<OrderLine> orderLines = orderLineDao.getOrderLinesByOrderId(order.getId());
        if (orderLines.isEmpty()) {
            order.setOrderStatus("Basket");
        } else {
            order.setOrderStatus("Ordered");
        }
        orderDao.updateOrder(order);
    }
}
