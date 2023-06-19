package com.jetbrains.aliye.pizza.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is a Spring MVC controller class that is responsible for handling HTTP requests related to online store.
 * This class makes uses of synchronisation to cope with sudden surge of demand.
 */
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

    /**
     * This function registers a purchasing customer in the backend.
     * @param customer
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
        try {
            synchronized (cartLock) {
                customerService.registerCustomer(customer);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This function logs in a purchasing customer to the backend.
     * @param customer
     * @param username
     * @param password
     * @return
     */
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

    /**
     * This function browses the entire Pizza catalog.
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas(){
        try{
            List<Pizza> pizzas = pizzaService.getPizzaCatalog();
            return ResponseEntity.ok(pizzas);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This function searches for Pizzas per Pizza category.
     * @param category
     * @return
     */
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

    /**
     * This function searches for Pizzas having specific terms in their title and description.
     * @param keyword
     * @return
     */
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

    /**
     * This function adds a Pizza to a customer's cart.
     * @param cartService
     * @param customer
     * @param pizza
     * @return
     */
    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestBody CartService cartService, @RequestBody Customer customer, @RequestBody Pizza pizza) {
        try {
            synchronized (cartLock){
                cartService.addToCart(customer, pizza);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This function removes a customer's Pizza from cart.
     * @param customer
     * @param pizza
     * @return
     */
    @PostMapping("/remove-from-cart")
    public ResponseEntity<String> removeFromCart(@RequestBody Customer customer, @RequestBody Pizza pizza) {
        try {
            synchronized (cartLock) {
                cartService.removeFromCart(customer, pizza);
                return ResponseEntity.ok("Pizza removed from cart");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This function updates a customer's cart pizza's quantity.
     * @param customer
     * @param pizza
     * @param quantity
     * @return
     */
    @PostMapping("/update-cart")
    public ResponseEntity<String> updateCart(@RequestBody Customer customer, Pizza pizza, int quantity) {
        try {
            synchronized (cartLock) {
                cartService.updateCartItemQuantity(customer, pizza, quantity);
                return ResponseEntity.ok("Cart updated");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This function logs a registered customer out.
     * @param customer
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logoutCustomer(@RequestBody Customer customer) {
        try {
            synchronized (cartLock) {
                customerService.logoutCustomer(customer);
                return ResponseEntity.ok("Logout successful");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This function checks out the cart and gives the total price for purchases.
     * @param customer
     * @return
     */
    @PostMapping("/checkout")
    public ResponseEntity<Double> checkoutCart(@RequestBody Customer customer) {
        try {
            synchronized (cartLock) {
                double totalPrice = cartService.checkoutCart(customer);
                return ResponseEntity.ok(totalPrice);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
