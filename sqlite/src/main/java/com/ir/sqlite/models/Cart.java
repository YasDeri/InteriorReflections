package com.ir.sqlite.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    List<Item> CartItems;
    public Cart() {
        CartItems = new ArrayList<>();
    }
    public void add(Item x) {
        CartItems.add(x);
    }

    public void remove(Item x) {
        CartItems.remove(x);
    }

    public List<Item> getItems() {
        return CartItems;
    }
}
