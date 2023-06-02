package com.Plantizza.VeganPizzeria.controllers;

import com.Plantizza.VeganPizzeria.dao.CustomerDao;
import com.Plantizza.VeganPizzeria.dao.OrderDao;
import com.Plantizza.VeganPizzeria.dao.OrderLineDao;
import com.Plantizza.VeganPizzeria.entities.Order;
import com.Plantizza.VeganPizzeria.entities.OrderLine;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class OrderController {

    @Autowired
    OrderDao orderDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    OrderLineDao orderLineDao;

    @GetMapping("customerTrackOrder")
    public String displayStatusToCustomer(@RequestParam(name = "id") String id, Model model) {

        //LocalDate today = LocalDate.now(); Add when there is an order for today
        int customerId = Integer.parseInt(id);
        System.out.println("I am customer " + customerId);
        LocalDate today = LocalDate.parse("2023-06-02"); //Remove when in production

        List<Order> orders = orderDao.getAllOrdersByCustomerIdByDate(customerId,today);
        model.addAttribute("orders", orders);


        return "customerTrackOrder";
    }

    @GetMapping("cookTrackOrder")
    public String displayStatusToCook (Model model) {
        //LocalDate today = LocalDate.now(); Add when there is an order for today
        LocalDate today = LocalDate.parse("2023-06-02"); //Remove when in production
        List<Order> orders = orderDao.getAllOrdersByDateForCook(today);
        List<OrderLine> orderLines = orderLineDao.getAllOrderLines();
        List<String> availableStatusToCook = Stream.of("Ordered","Cooking", "Pick up")
                        .collect(Collectors.toList());
        model.addAttribute("orders", orders);
        model.addAttribute("orderLines", orderLines);
        model.addAttribute("availableStatusToCook", availableStatusToCook);

        return "cookTrackOrder";
    }

    @GetMapping("orderDetail")
    public String orderDetail(Integer id, Model model) {
        Order order = orderDao.getOrderById(id);
        List<OrderLine> orderLines = orderLineDao.getOrderLinesByOrderId(id);
        model.addAttribute("order", order);
        model.addAttribute("orderLines", orderLines);
        return "orderDetail";
    }

    @PostMapping("editStatus")
    public String editStatus(String status, String id){
        System.out.println("My order Id is "+id);
        System.out.println("My status is "+status);
        int orderId = Integer.parseInt(id);
        Order order = orderDao.getOrderById(orderId);
        order.setOrderStatus(status);
        orderDao.updateOrder(order);
        return "redirect:/cookTrackOrder";
    }

}
