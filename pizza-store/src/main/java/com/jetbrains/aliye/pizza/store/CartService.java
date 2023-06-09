package com.jetbrains.aliye.pizza.store;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
   private List<Cart> carts;

   public CartService(){
       carts = new ArrayList<>();
   }
}
