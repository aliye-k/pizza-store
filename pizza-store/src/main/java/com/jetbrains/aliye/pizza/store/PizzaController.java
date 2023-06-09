package com.jetbrains.aliye.pizza.store;

public class PizzaController {

    private final CustomerService customerService;
    private final PizzaService pizzaService;
    private final CartService cartService;

    public PizzaController(CustomerService customerService,PizzaService pizzaService,CartService cartService){
        this.customerService = customerService;
        this.pizzaService = pizzaService;
        this.cartService = cartService;
    }


}
