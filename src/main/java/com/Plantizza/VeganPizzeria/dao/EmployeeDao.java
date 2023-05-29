package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Employee;

import java.util.List;

public interface EmployeeDao {
    Employee getEmployeeByID(int id);
    Employee getEmployeeByEmail(String email);
    List<Employee> getAllEmployees();
    Employee addEmployee(Employee employee);
    void updateEmployee(Employee employee);
    void deleteEmployeeById(int id);
}
