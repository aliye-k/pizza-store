package com.jetbrains.aliye.pizza.store;

import java.util.List;

public class Cart {
    private Customer customer;
    private List<Pizza> pizzas;
    private double totalPrice = 0.0;

    public Cart(Customer customer, List<Pizza> pizzas) {
        this.customer = customer;
        this.pizzas = pizzas;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public double getTotalPrice() {
        for (Pizza pizza : pizzas) {
            totalPrice += pizza.getPrice() * pizza.getQuantity();
        }
        return totalPrice;
    }
}
