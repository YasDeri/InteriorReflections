package com.ir.sqlite.models;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
    List<Item> items;

    public ItemList(){
        items = new ArrayList<>();
    }

    public ItemList(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}
