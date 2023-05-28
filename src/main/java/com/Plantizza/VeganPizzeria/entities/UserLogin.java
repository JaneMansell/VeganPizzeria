package com.Plantizza.VeganPizzeria.entities;

import java.util.Objects;

public class UserLogin {
    private String emailAddress;
    private String password;
    private String userType;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLogin userLogin = (UserLogin) o;
        return Objects.equals(emailAddress, userLogin.emailAddress) && Objects.equals(password, userLogin.password) && Objects.equals(userType, userLogin.userType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress, password, userType);
    }
}
