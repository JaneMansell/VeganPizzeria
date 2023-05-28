package com.Plantizza.VeganPizzeria.entities;

import java.util.Objects;

public class Login {
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
        Login login = (Login) o;
        return Objects.equals(emailAddress, login.emailAddress) && Objects.equals(password, login.password) && Objects.equals(userType, login.userType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress, password, userType);
    }
}
