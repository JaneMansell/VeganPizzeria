package com.Plantizza.VeganPizzeria.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Pizza {
    private String pizzaName;
    private BigDecimal pizzaPrice;
    private String pizzaDesc;
    private String pizzaImgURL;

    public String getPizzaName() { return pizzaName; }

    public void setPizzaName(String pizzaName) { this.pizzaName = pizzaName; }

    public BigDecimal getPizzaPrice() { return pizzaPrice; }

    public void setPizzaPrice(BigDecimal pizzaPrice) { this.pizzaPrice = pizzaPrice; }

    public String getPizzaDesc() {
        return pizzaDesc;
    }

    public void setPizzaDesc(String pizzaDesc) {
        this.pizzaDesc = pizzaDesc;
    }

    public String getPizzaImgURL() {
        return pizzaImgURL;
    }

    public void setPizzaImgURL(String pizzaImgURL) {
        this.pizzaImgURL = pizzaImgURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza pizza)) return false;
        return Objects.equals(pizzaName, pizza.pizzaName) && Objects.equals(pizzaPrice, pizza.pizzaPrice) && Objects.equals(pizzaDesc, pizza.pizzaDesc) && Objects.equals(pizzaImgURL, pizza.pizzaImgURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pizzaName, pizzaPrice, pizzaDesc, pizzaImgURL);
    }
}
