package com.jetbrains.aliye.pizza.store;

/**
 * This class represents a customer entity with attributes like ID, name, username, password,
 * and whether a customer is logged in. It encapsulates information about a customer.
 */
public class Customer {
    private int id;
    private String name;
    private String email;
    private String password;
    private boolean isLoggedIn = true;

    public Customer(int id, String name, String email, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public boolean getIsLoggedIn(){
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn){
        this.isLoggedIn = loggedIn;
    }
}
