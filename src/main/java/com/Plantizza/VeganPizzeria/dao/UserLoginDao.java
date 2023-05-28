package com.Plantizza.VeganPizzeria.dao;

import com.Plantizza.VeganPizzeria.entities.UserLogin;

import java.util.List;

public interface UserLoginDao {
    UserLogin getuserLoginByID(int id);
    List<UserLogin> getAlluserLogins();
    UserLogin adduserLogin(UserLogin userLogin);
    void updateuserLogin(UserLogin userLogin);
    void deleteuserLoginById(int id);
}
