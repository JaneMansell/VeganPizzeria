package com.Plantizza.VeganPizzeria.controllers;

import com.Plantizza.VeganPizzeria.dao.CustomerDao;
import com.Plantizza.VeganPizzeria.dao.EmployeeDao;
import com.Plantizza.VeganPizzeria.dao.UserLoginDao;
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

    @PostMapping("inputLoginDetails")
    public String inputLoginDetails(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserLogin user = userLoginDao.getuserLoginByEmailAddress(email);
        if (user == null){
            return "redirect:/login";
        }
        else if (!user.getPassword().equals(password)){
            return "redirect:/login";
        }
        else {
            String status = user.getUserType();
            System.out.println("I am a "+ status);
            if (status.equalsIgnoreCase("customer")){
                //this will need to take the customer ID with it.
                return "redirect:/customerMenu";
            }
            else if (status.equalsIgnoreCase("Employee")){
                return "redirect:/employeeMenu";
            }
            else {
                //do something else temporary solution
                return "redirect:/login";
            }
        }

    }
}

