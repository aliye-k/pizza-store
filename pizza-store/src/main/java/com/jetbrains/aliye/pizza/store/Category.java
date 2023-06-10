package com.jetbrains.aliye.pizza.store;

public class Category {
    private boolean vegetarian;

    public Category(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean getCategory() {
        return vegetarian;
    }

    public void setCategory(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

}
