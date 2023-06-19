package com.jetbrains.aliye.pizza.store;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for managing pizza-related operations, such as retrieving the pizza catalog,
 * searching pizzas by category or keyword, and handling pizza-related data.
 * This class makes uses of synchronisation to cope with sudden surge of demand.
 */
@Service
public class PizzaService {
    private final List<Pizza> pizzaCatalog;
    private final Object pizzaLock = new Object();

    public PizzaService() {
        synchronized (pizzaLock) {
            pizzaCatalog = new ArrayList<>();
        }
    }

    public void addToCatalog(Pizza pizza) {
        synchronized (pizzaLock) {
            pizzaCatalog.add(pizza);
        }
    }

    public List<Pizza> getPizzaCatalog() {
        synchronized (pizzaLock) {
            return pizzaCatalog;
        }
    }

    public List<Pizza> searchByCategory(Category category) {
        synchronized (pizzaLock) {
            List<Pizza> pizzaByCategory = new ArrayList<>();
            for (Pizza pizza : pizzaCatalog) {
                if (pizza.getCategory().getIsVegetarian() == category.getIsVegetarian()) {
                    pizzaByCategory.add(pizza);
                }
            }
            return pizzaByCategory;
        }
    }

    public List<Pizza> searchByKeyword(String keyword) {
        synchronized (pizzaLock) {
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

}
