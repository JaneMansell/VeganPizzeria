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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserLoginController {

    @Autowired
    UserLoginDao userLoginDao;

    @Autowired
    CustomerDao customerDao;

    @GetMapping("login")
    public String displayLogin() {
        /* Method to display login page */
        return "login";
    }

    @GetMapping("signUp")
    public String displaySignUp(Model model) {
        /* Method to display sign up page */
        return "signUp";
    }

    @PostMapping("inputLoginDetails")
    public String inputLoginDetails(HttpServletRequest request) {
        /* Form that collects login details */
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserLogin user = userLoginDao.getuserLoginByEmailAddress(email);
        if (user == null) {
            // If the user is not in the database they are returned to the login page
            return "redirect:/login";
        } else if (!user.getPassword().equals(password)) {
            // If the user has put in the wrong password they are returned to the database
            return "redirect:/login";
        } else { // This is a valid user
            String status = user.getUserType();
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
        /* This method validates the new user and if validated adds them
        *  to the database. */
        // check if email already registered
        String email = request.getParameter("email");
        List<UserLogin> allUsers = userLoginDao.getAlluserLogins();
        List<String> emailAddresses = allUsers.stream()
                .map(UserLogin::getEmailAddress)
                .collect(Collectors.toList());
        if (emailAddresses.contains(email)) { // refreshes page if duplicate email. In the future, deal with this exception properly
            return "redirect:/signUp";
        }

        // grab remaining data from form
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String postCode = request.getParameter("postCode");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

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

    // code for future email validation. not currently in use.
    @GetMapping("/checkEmail")
    @ResponseBody
    public String checkEmailExists(@RequestParam("email") String email) {
        List<UserLogin> allUsers = userLoginDao.getAlluserLogins();
        List<String> emailAddresses = allUsers.stream()
                .map(UserLogin::getEmailAddress)
                .collect(Collectors.toList());

        if (emailAddresses.contains(email)) {
            return "exists";
        } else {
            return "notExists";
        }
    }
}

