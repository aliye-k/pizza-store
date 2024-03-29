package com.jetbrains.aliye.pizza.store;

import java.util.List;

/**
 * This class represents a pizza entity with attributes like ID, name, price, description, category,
 * and toppings. It encapsulates information about a pizza.
 */
public class Pizza {
    private int id;
    private String name;
    private double price;
    private String description;
    private Category category;
    private List<Topping> toppings;

    private int quantity = 0;

    public Pizza(int id, String name, double price, String description,  Category category, List<Topping> toppings) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.toppings = toppings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int newQuantity){
        this.quantity = newQuantity;
    }
}
