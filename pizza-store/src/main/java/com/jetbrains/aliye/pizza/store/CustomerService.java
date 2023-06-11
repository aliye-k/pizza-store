package com.jetbrains.aliye.pizza.store;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final List<Customer> customers;
    private final Object customerLock = new Object();

    public CustomerService(){
        customers = new ArrayList<>();
    }

    public void registerCustomer(Customer customer){
        synchronized (customerLock) {
            customers.add(customer);
        }
    }

    public Customer loginCustomer(int customerId){
        synchronized (customerLock) {
            for (Customer customer : customers) {
                if (customer.getId() == customerId)
                    return customer;
            }
            return null;
        }
    }

    public Customer signup(String username, String email, String password) {
        synchronized (customerLock) {
            //Check if the email is already registered
            if (isEmailRegistered(email)) {
                throw new IllegalArgumentException("Email is already registered");
            }
            //Set the customer ID
            int Id = generateCustomerId();
            //Create a new customer object
            Customer newCustomer = new Customer(Id, username, email, password);
            return newCustomer;
        }
    }

    public boolean loginSuccessful(Customer customer, String username, String password){
        synchronized (customerLock) {
            return customer.getName().equals(username) && customer.getPassword().equals(password);
        }
    }

    private boolean isEmailRegistered(String email) {
        //Check if the email is already registered in the existing customers list
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private int generateCustomerId() {
        //generate a unique customer ID
        //For simplicity, let's assume it increments the ID by 1 each time
        if (customers.isEmpty()) {
            return 1;
        }
        int lastCustomerId = customers.get(customers.size() - 1).getId();
        return lastCustomerId + 1;
    }

    public List<Customer> getCustomer(){
        synchronized (customerLock) {
            return customers;
        }
    }

    public void logoutCustomer(Customer customer) {
        synchronized (customerLock) {
            customer.setLoggedIn(false);
        }
    }
}
