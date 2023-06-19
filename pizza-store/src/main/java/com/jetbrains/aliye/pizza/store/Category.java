package com.jetbrains.aliye.pizza.store;

/**
 * This class represents a pizza category entity, indicating whether a pizza is vegetarian or not.
 */
public class Category {
    private boolean vegetarian;

    public Category(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean getIsVegetarian() {
        return vegetarian;
    }

    public void setCategory(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

}
