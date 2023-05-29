package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderDaoDBTest {

    @Autowired
    OrderDao orderdao;

    @BeforeEach
    public void setUp() {
        List<Order> orders =orderdao.getAllOrders();
        for (Order order : orders){
            orderdao.deleteOrderById(order.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllAndAddOrders() {
        Order order = new Order();
        order.setCustomerId(1);
        order.setOrderPlacedTime(LocalTime.parse("17:07:20"));
        order.setOrderDate(LocalDate.parse("2023-05-29"));
        order.setTotal(BigDecimal.valueOf(18.99));
        order.setOrderStatus("Cooking");
        order = orderdao.addOrder(order);

        Order order2 = new Order();
        order2.setCustomerId(3);
        order2.setOrderPlacedTime(LocalTime.parse("17:07:25"));
        order2.setOrderDate(LocalDate.parse("2023-05-29"));
        order2.setTotal(BigDecimal.valueOf(25.99));
        order2.setOrderStatus("Delivered");
        order2 = orderdao.addOrder(order2);

        List<Order> orders = orderdao.getAllOrders();

        assertEquals(2, orders.size());
        assertTrue(orders.contains(order));
        assertTrue(orders.contains(order2));
    }

    @Test
    public void testAddAndGetOrder(){
        Order order = new Order();
        order.setCustomerId(1);
        order.setOrderPlacedTime(LocalTime.parse("17:07:20"));
        order.setOrderDate(LocalDate.parse("2023-05-29"));
        order.setTotal(BigDecimal.valueOf(18.99));
        order.setOrderStatus("Cooking");
        order = orderdao.addOrder(order);

        Order fromDao = orderdao.getOrderByID(order.getId());
        System.out.println("Is this the time: " + fromDao.getOrderPlacedTime());

        assertEquals(order, fromDao);
    }

    @Test
    public void getAllOrdersByCustomerIdByDate() {
        Order order = new Order();
        order.setCustomerId(1);
        order.setOrderPlacedTime(LocalTime.parse("17:07:20"));
        order.setOrderDate(LocalDate.parse("2023-05-29"));
        order.setTotal(BigDecimal.valueOf(18.99));
        order.setOrderStatus("Cooking");
        order = orderdao.addOrder(order);

        Order order2 = new Order();
        order2.setCustomerId(1);
        order2.setOrderPlacedTime(LocalTime.parse("19:07:25"));
        order2.setOrderDate(LocalDate.parse("2023-05-29"));
        order2.setTotal(BigDecimal.valueOf(25.99));
        order2.setOrderStatus("Delivered");
        order2 = orderdao.addOrder(order2);

        List<Order> orders = orderdao.getAllOrdersByCustomerIdByDate(1, LocalDate.parse("2023-05-29"));

        assertEquals(2, orders.size());
        assertTrue(orders.contains(order));
        assertTrue(orders.contains(order2));
    }
}