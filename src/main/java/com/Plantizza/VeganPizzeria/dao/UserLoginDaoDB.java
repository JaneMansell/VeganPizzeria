package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.UserLogin;
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
public class UserLoginDaoDB implements UserLoginDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public UserLogin getuserLoginByEmailAddress(String email) {
        try{
            final String GET_LOGIN_BY_EMAIL = "SELECT * FROM login WHERE emailAddress = ?";
            return jdbc.queryForObject(GET_LOGIN_BY_EMAIL, new UserLoginMapper(), email);
        } catch(DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<UserLogin> getAlluserLogins() {
        final String GET_ALL_LOGINS = "SELECT * FROM login";
        return jdbc.query(GET_ALL_LOGINS, new UserLoginMapper());
    }

    @Override
    @Transactional
    public UserLogin adduserLogin(UserLogin userLogin) {
        final String INSERT_LOGIN ="INSERT INTO login(" +
                "emailAddress, password," +
                "userType) VALUES (?,?,?)";
        jdbc.update(INSERT_LOGIN,
                userLogin.getEmailAddress(),
                userLogin.getPassword(),
                userLogin.getUserType());

        return userLogin;
    }

    @Override
    public void updateuserLogin(UserLogin userLogin) {
        final String UPDATE_LOGIN = "UPDATE login SET " +
                "password =?, userType = ? WHERE emailAddress = ?";
        jdbc.update(UPDATE_LOGIN,
                userLogin.getPassword(),
                userLogin.getUserType(),
                userLogin.getEmailAddress());
    }

    @Override
    @Transactional
    public void deleteuserLoginByEmailAddress(String email) {
        final String DELETE_LOGIN ="DELETE FROM login WHERE emailAddress = ?";
        jdbc.update(DELETE_LOGIN, email);

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
