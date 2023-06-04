package com.Plantizza.VeganPizzeria.controllers;

import com.Plantizza.VeganPizzeria.dao.CustomerDao;
import com.Plantizza.VeganPizzeria.dao.EmployeeDao;
import com.Plantizza.VeganPizzeria.dao.UserLoginDao;
import com.Plantizza.VeganPizzeria.entities.Customer;
import com.Plantizza.VeganPizzeria.entities.UserLogin;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserLoginController {

    @Autowired
    UserLoginDao userLoginDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    EmployeeDao employeeDao;

    @GetMapping("login")
    public String displayLogin() {
        return "login";
    }

    @GetMapping("signUp")
    public String displaySignUp(Model model) {
        List<UserLogin> allUsers = userLoginDao.getAlluserLogins();
        model.addAttribute("allUsers", allUsers);
        return "signUp";
    }

    @PostMapping("inputLoginDetails")
    public String inputLoginDetails(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserLogin user = userLoginDao.getuserLoginByEmailAddress(email);
        if (user == null) {
            return "redirect:/login";
        } else if (!user.getPassword().equals(password)) {
            return "redirect:/login";
        } else {
            String status = user.getUserType();
            System.out.println("I am a " + status);
            if (status.equalsIgnoreCase("customer")) {
                //this needs to take the customer ID with it.
                Customer customer = customerDao.getCustomerByEmail(email);
                int customerId = customer.getCustomerId();
                return "redirect:/customerMenu/" + customerId;
            } else if (status.equalsIgnoreCase("Employee")) {
                return "redirect:/employeeMenu";
            } else {
                //in the future this will be admin user
                return "redirect:/login";
            }
        }

    }

    @PostMapping("registerNewCustomer")
    public String registerNewCustomer(HttpServletRequest request, Model model) {
        // grab data from form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String postCode = request.getParameter("postCode");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        /*// check if email already exists in database
        List<UserLogin> allUsers = (List<UserLogin>) model.getAttribute("allUsers");
        for (UserLogin user : allUsers) {
            if (user.getEmailAddress().equals(email)) {
                // make user have to use another email
            }
        }*/

        // create and add userLogin object
        UserLogin userLogin = new UserLogin();
        userLogin.setUserType("Customer");
        userLogin.setEmailAddress(email);
        userLogin.setPassword(password);
        userLogin = userLoginDao.adduserLogin(userLogin);

        // create and add customer object
        Customer customer = new Customer();
        customer.setCustomerName(name);
        customer.setCustomerEmailAddress(email);
        customer.setCustomerPhone(phone);
        customer.setCustomerFirstLineAddress(address);
        customer.setCustomerPostCode(postCode);
        customer = customerDao.addCustomer(customer);

        return "registerSuccess";
    }
}

