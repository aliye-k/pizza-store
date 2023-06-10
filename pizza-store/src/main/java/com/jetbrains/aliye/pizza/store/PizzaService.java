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
}
