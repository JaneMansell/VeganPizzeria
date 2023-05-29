package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.UserLogin;

import java.util.List;

public interface UserLoginDao {
    UserLogin getuserLoginByEmailAddress(String email);
    List<UserLogin> getAlluserLogins();
    UserLogin adduserLogin(UserLogin userLogin);
    void updateuserLogin(UserLogin userLogin);
    void deleteuserLoginByEmailAddress(String email);
}
