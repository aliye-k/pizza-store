package com.jetbrains.aliye.pizza.store;

public class Category {
    private boolean vegetarian;

    public Category(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }
}
