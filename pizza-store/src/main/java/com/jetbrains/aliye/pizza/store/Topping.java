package com.jetbrains.aliye.pizza.store;

/**
 * This class represents a pizza topping entity, including name and description of the toppings.
 */
public class Topping {
    private String name;
    private String description;

    public Topping(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
