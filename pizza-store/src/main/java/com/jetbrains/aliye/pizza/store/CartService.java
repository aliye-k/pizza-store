package com.jetbrains.aliye.pizza.store;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
   private final List<Cart> carts;
   private final Object cartLock = new Object();

   public CartService(){
       carts = new ArrayList<>();
   }

   public void addToCart(Customer customer, Pizza pizza){
       synchronized (cartLock) {
           //Check if the customer already has a cart:
           Cart cart = findCartByCustomer(customer);
           //if the customer doesn't have a cart then create a new one:
           if (cart == null) {
               cart = new Cart(customer, new ArrayList<>());
               carts.add(cart);
           }
           //add the pizza to the customer's cart:
           cart.getPizzas().add(pizza);
           //get all the pizzas of the cart and if the pizza already exists, then increase the quantity by 1
           List<Pizza> pizzas = cart.getPizzas();
           for (Pizza p : pizzas)
               if (p.equals(pizza))
                   p.setQuantity(p.getQuantity() + 1);
       }
   }

   public void removeFromCart(Customer customer, Pizza pizza){
       synchronized (cartLock) {
           //find the cart of the customer:
           Cart cart = findCartByCustomer(customer);
           if (cart != null) {
               cart.getPizzas().remove(pizza);
           }
       }
   }

   public void updateCartItemQuantity(Customer customer, Pizza pizza, int quantity){
       synchronized (cartLock) {
           //find the cart of the customer:
           Cart cart = findCartByCustomer(customer);
           if (cart != null) {
               //get the pizzas of the cart
               List<Pizza> pizzas = cart.getPizzas();
               if (quantity > 0) {
                   for (Pizza p : pizzas)
                       if (p.equals(pizza))
                           p.setQuantity(p.getQuantity() + quantity);
               } else pizzas.remove(pizza);
           }
       }
   }

    private Cart findCartByCustomer(Customer customer) {
       for (Cart cart : carts)
           if(cart.getCustomer().equals(customer))
               return cart;
       return null;
    }

    public double checkoutCart(Customer customer) {
        synchronized (cartLock) {
            double totalPrice = 0.0;
            Cart cart = findCartByCustomer(customer);
            if (cart != null && !cart.getPizzas().isEmpty()) {
                //Calculate the total price
                totalPrice = cart.getTotalPrice();
                //Perform necessary operations for checkout (e.g., update inventory, generate order, etc.)

                //Clear the cart after successful checkout
                cart.getPizzas().clear();
            }
            return totalPrice;
        }
    }

}
