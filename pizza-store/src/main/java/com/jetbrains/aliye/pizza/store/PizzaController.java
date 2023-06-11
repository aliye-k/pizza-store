package com.jetbrains.aliye.pizza.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    private final CustomerService customerService;
    private final PizzaService pizzaService;
    private final CartService cartService;

    private final Object cartLock = new Object();
    private final Object searchLock = new Object();

    @Autowired
    public PizzaController(CustomerService customerService,PizzaService pizzaService,CartService cartService){
        this.customerService = customerService;
        this.pizzaService = pizzaService;
        this.cartService = cartService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
        try {
            synchronized (cartLock) {
                // Register the customer
                customerService.registerCustomer(customer);

                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> loginCustomer(@RequestBody Customer customer,@RequestBody String username,@RequestBody String password) {
        try {
            synchronized (cartLock) {
                // Authenticate the customer
                boolean isAuthenticated = customerService.loginSuccessful(customer, username, password);
                if (isAuthenticated) {
                    Customer customerLoggedIn = customerService.loginCustomer(customer.getId());
                    return ResponseEntity.ok(customerLoggedIn);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas(){
        try{
            List<Pizza> pizzas = pizzaService.getPizzaCatalog();
            return ResponseEntity.ok(pizzas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(params = "category")
    public ResponseEntity<List<Pizza>> getPizzasByCategory(@RequestParam Category category){
        try {
            synchronized (searchLock) {
                List<Pizza> pizzas = pizzaService.searchByCategory(category);
                return ResponseEntity.ok(pizzas);
            }
        } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }

    @GetMapping(params = "keyword")
    public ResponseEntity<List<Pizza>> searchPizzasByKeyword(@RequestParam String keyword) {
        try {
            synchronized (searchLock) {
                List<Pizza> pizzas = pizzaService.searchByKeyword(keyword);
                return ResponseEntity.ok(pizzas);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestBody CartService cartService, @RequestBody Customer customer, @RequestBody Pizza pizza) {
        try {
            synchronized (cartLock){
                //Add the pizza to the customer's cart
                cartService.addToCart(customer, pizza);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/remove-from-cart")
    public ResponseEntity<String> removeFromCart(@RequestBody Customer customer, @RequestBody Pizza pizza) {
        try {
            synchronized (cartLock) {
                // Remove the pizza from the customer's cart
                cartService.removeFromCart(customer, pizza);

                return ResponseEntity.ok("Pizza removed from cart");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/update-cart")
    public ResponseEntity<String> updateCart(@RequestBody Customer customer, Pizza pizza, int quantity) {
        try {
            synchronized (cartLock) {
                // Update the pizza quantity in the customer's cart
                cartService.updateCartItemQuantity(customer, pizza, quantity);

                return ResponseEntity.ok("Cart updated");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutCustomer(@RequestBody Customer customer) {
        try {
            synchronized (cartLock) {
                // Perform logout logic for the customer
                customerService.logoutCustomer(customer);
                return ResponseEntity.ok("Logout successful");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<Double> checkoutCart(@RequestBody Customer customer) {
        try {
            synchronized (cartLock) {
                // Perform checkout logic for the customer's cart
                double totalPrice = cartService.checkoutCart(customer);
                return ResponseEntity.ok(totalPrice);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
