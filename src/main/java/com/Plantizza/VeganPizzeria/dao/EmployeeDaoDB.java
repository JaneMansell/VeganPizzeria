package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.Employee;
import com.Plantizza.VeganPizzeria.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDaoDB implements EmployeeDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Employee getEmployeeByID(int id) {
        try{
            final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE employeeId = ?";
            return jdbc.queryForObject(GET_EMPLOYEE_BY_ID, new EmployeeMapper(), id);
        } catch(DataAccessException e) {
            return null;
        }
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        try{
            final String GET_EMPLOYEE_BY_EMAIL = "SELECT * FROM employees WHERE emailAddress = ?";
            return jdbc.queryForObject(GET_EMPLOYEE_BY_EMAIL, new EmployeeMapper(), email);
        } catch(DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        final String GET_ALL_EMPLOYEES = "SELECT * FROM employees";
        return jdbc.query(GET_ALL_EMPLOYEES, new EmployeeMapper());
    }

    @Override
    @Transactional
    public Employee addEmployee(Employee employee) {
        final String INSERT_ORDER ="INSERT INTO employees(" +
                "firstName, secondName," +
                "emailAddress) VALUES (?,?,?)";
        jdbc.update(INSERT_ORDER,
                employee.getFirstName(),
                employee.getSecondName(),
                employee.getEmailAddress());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        employee.setId(newId);

        return employee;
    }

    @Override
    public void updateEmployee(Employee employee) {
        final String UPDATE_EMPLOYEE = "UPDATE employees SET firstName = ?, " +
                "secondName =?, emailAddress = ? WHERE employeeId = ?";
        jdbc.update(UPDATE_EMPLOYEE,
                employee.getFirstName(),
                employee.getSecondName(),
                employee.getEmailAddress(),
                employee.getId());
    }

    @Override
    @Transactional
    public void deleteEmployeeById(int id) {
        final String DELETE_EMPLOYEE ="DELETE FROM employees WHERE employeeId = ?";
        jdbc.update(DELETE_EMPLOYEE,id);
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
