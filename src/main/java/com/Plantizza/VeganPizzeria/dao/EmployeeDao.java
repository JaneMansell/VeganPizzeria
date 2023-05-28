package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Employee;
import com.Plantizza.VeganPizzeria.entities.Order;

import java.util.List;

public interface EmployeeDao {
    Employee getEmployeeByID(int id);
    Employee getEmployeeByEmail(String string);
    List<Employee> getAllEmployees();
    Order addEmployee(Employee employee);
    void updateEmployee(Employee employee);
    void deleteEmployeeById(int id);
}
