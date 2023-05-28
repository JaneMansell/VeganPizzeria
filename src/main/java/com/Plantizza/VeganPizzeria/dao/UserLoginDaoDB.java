package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserLoginDaoDB implements UserLoginDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public UserLogin getuserLoginByID(int id) {
        return null;
    }

    @Override
    public List<UserLogin> getAlluserLogins() {
        return null;
    }

    @Override
    public UserLogin adduserLogin(UserLogin userLogin) {
        return null;
    }

    @Override
    public void updateuserLogin(UserLogin userLogin) {

    }

    @Override
    public void deleteuserLoginById(int id) {

    }

    public static final class UserLoginMapper implements RowMapper<UserLogin> {

        @Override
        public UserLogin mapRow(ResultSet rs, int index) throws SQLException {
            UserLogin userLogin = new UserLogin();
            userLogin.setEmailAddress(rs.getString("emailAddress"));
            userLogin.setPassword(rs.getString("password"));
            userLogin.setUserType(rs.getString("userType"));

            return userLogin;
        }
    }
}
