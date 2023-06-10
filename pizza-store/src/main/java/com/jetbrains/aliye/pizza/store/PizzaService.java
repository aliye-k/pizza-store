package com.jetbrains.aliye.pizza.store;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaService {
    private List<Pizza> pizzaCatalog;

    public PizzaService() {
        pizzaCatalog = new ArrayList<>();
    }

    public void addToCatalog(Pizza pizza) {
        pizzaCatalog.add(pizza);
    }

    public List<Pizza> getPizzaCatalog() {
        return pizzaCatalog;
    }

    public List<Pizza> searchByCategory(Category category) {
        List<Pizza> pizzaByCategory = new ArrayList<>();
        for (Pizza pizza : pizzaCatalog) {
            if (pizza.getCategory().getIsVegetarian() == category.getIsVegetarian()) {
                pizzaByCategory.add(pizza);
            }
        }
        return pizzaByCategory;
    }

    public List<Pizza> searchByKeyword(String keyword) {
        List<Pizza> pizzaByKeyword = new ArrayList<>();
        for (Pizza pizza : pizzaCatalog) {
            if (pizza.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    pizza.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                pizzaByKeyword.add(pizza);
            }
        }
        return pizzaByKeyword;
    }

}
