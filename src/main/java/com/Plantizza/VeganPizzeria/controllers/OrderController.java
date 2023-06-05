package com.Plantizza.VeganPizzeria.controllers;

import com.Plantizza.VeganPizzeria.dao.CustomerDao;
import com.Plantizza.VeganPizzeria.dao.OrderDao;
import com.Plantizza.VeganPizzeria.dao.OrderLineDao;
import com.Plantizza.VeganPizzeria.entities.DuckPic;
import com.Plantizza.VeganPizzeria.entities.Order;
import com.Plantizza.VeganPizzeria.entities.OrderLine;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;
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
    public String displayStatusToCustomer(@RequestParam(name = "id") String id, Model model) throws IOException, InterruptedException {

        LocalDate today = LocalDate.now();
        int customerId = Integer.parseInt(id);
        System.out.println("I am customer " + customerId);
        List<Order> orders = orderDao.getAllOrdersByCustomerIdByDate(customerId,today);
        for (Order order: orders) {
            switch (order.getOrderStatus()){

                case "Basket":
                    // Message to say order has not been submitted
                    order.setOrderStatus("You have not clicked the submit order button");
                    break;
                case "Ordered":
                    // Message to say pizza is being prepared
                    order.setOrderStatus("Your pizzas are being lovingly hand-prepared by our Sicilian chef");
                    break;
                case "Cooking":
                    // Message to say pizza is being cooked
                    order.setOrderStatus("Your pizzas are cooking at the moment");
                    break;
                case "Pick up":
                    // A message that doesn't imply pizza is sat on the side getting cold
                    order.setOrderStatus("Your pizzas are on their way");
                    break;
                case "Picked Up":
                    // A message to say the pizza its on its way
                    order.setOrderStatus("Your pizzas are on their way to you");
                    break;
                case "Delivered":
                    // A message to say the pizza has been delivered
                    order.setOrderStatus("Your pizzas have been delivered");
                    break;
                default:
                    // this must be an error but give customer friendly message
                    order.setOrderStatus("Oops! Something has gone wrong. Please try again.");
            }
        }
        model.addAttribute("orders", orders);
        // Animal Fact from API
        //Build get request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://random-d.uk/api/random"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        //Get response from endpoint
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        //Turn response body into a json String
        String responseString = response.body().toString();
        //Use Google Gson to map the json string to a java object
        //This required an extra dependency in the pom file
        Gson gson = new Gson();
        DuckPic duckpic = gson.fromJson(responseString, DuckPic.class);
        //Add duckpic object to model. Use Thymeleaf in html to get URL.
        model.addAttribute("duckpic", duckpic);

        return "customerTrackOrder";
    }

    @GetMapping("cookTrackOrder")
    public String displayStatusToCook (Model model) {
        LocalDate today = LocalDate.now();
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
