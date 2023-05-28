package com.Plantizza.VeganPizzeria.entities;

import java.util.Objects;

public class Employees {
    private int id;
    private String firstName;
    private String secondName;
    private String emailAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employees employees = (Employees) o;
        return id == employees.id && Objects.equals(firstName, employees.firstName) && Objects.equals(secondName, employees.secondName) && Objects.equals(emailAddress, employees.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, emailAddress);
    }
}
