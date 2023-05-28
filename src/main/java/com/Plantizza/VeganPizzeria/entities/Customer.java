package com.Plantizza.VeganPizzeria.entities;

import java.util.Objects;

public class Customer {

    private int customerId;
    private String customerName;
    private String customerEmailAddress;
    private String customerFirstLineAddress;
    private String customerPostCode;
    private String customerPhone;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }

    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmailAddress() { return customerEmailAddress; }

    public void setCustomerEmailAddress(String customerEmailAddress) { this.customerEmailAddress = customerEmailAddress; }

    public String getCustomerFirstLineAddress() {
        return customerFirstLineAddress;
    }

    public void setCustomerFirstLineAddress(String customerFirstLineAddress) {
        this.customerFirstLineAddress = customerFirstLineAddress;
    }

    public String getCustomerPostCode() { return customerPostCode; }

    public void setCustomerPostCode(String customerPostCode) { this.customerPostCode = customerPostCode; }

    public String getCustomerPhone() { return customerPhone; }

    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return customerId == customer.customerId && Objects.equals(customerName, customer.customerName) && Objects.equals(customerEmailAddress, customer.customerEmailAddress) && Objects.equals(customerPostCode, customer.customerPostCode) && Objects.equals(customerPhone, customer.customerPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, customerEmailAddress, customerPostCode, customerPhone);
    }
}
