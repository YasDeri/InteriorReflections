package com.ir.sqlite.models;

import java.util.ArrayList;
import java.util.List;

public class ItemCategory {
    String name;
    List<Item> items = new ArrayList<>();

    public ItemCategory(String name) {
        this.name = name;
    }

    public ItemCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addItem(Item x) {
        items.add(x);
    }

    public void removeItem(Item x) {
        items.remove(x);
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItems(List<Item> its) {
        for(int i=0; i<its.size(); i++) {
            items.add(its.get(i));
        }
    }
}