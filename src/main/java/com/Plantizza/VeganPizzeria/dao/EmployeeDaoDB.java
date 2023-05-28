package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Employee;
import com.Plantizza.VeganPizzeria.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDaoDB implements EmployeeDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Employee getEmployeeByID(int id) {
        return null;
    }

    @Override
    public Employee getEmployeeByEmail(String string) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public Order addEmployee(Employee employee) {
        return null;
    }

    @Override
    public void updateEmployee(Employee employee) {

    }

    @Override
    public void deleteEmployeeById(int id) {

    }

    public static final class EmployeeMapper implements RowMapper<Employee>{

        @Override
        public Employee mapRow(ResultSet rs, int index) throws SQLException{
            Employee employee = new Employee();
            employee.setId(rs.getInt("employeeId"));
            employee.setFirstName(rs.getString("firstName"));
            employee.setSecondName(rs.getString("secondName"));
            employee.setEmailAddress(rs.getString("emailAddress"));

            return employee;
        }
    }
}
