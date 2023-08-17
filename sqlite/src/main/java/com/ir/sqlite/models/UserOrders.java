package com.ir.sqlite.models;

import java.util.List;

public class UserOrders {
    String username;
    List<Order> orders;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserOrders() {
    }

    public UserOrders(String username, List<Order> orders) {
        this.username = username;
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
