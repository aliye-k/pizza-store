package com.jetbrains.aliye.pizza.store;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private List<Customer> customers;

    public CustomerService(){
        customers = new ArrayList<>();
    }
}
