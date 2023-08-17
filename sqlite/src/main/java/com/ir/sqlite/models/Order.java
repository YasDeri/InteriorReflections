package com.ir.sqlite.models;

import java.util.List;
import java.util.Random;

public class Order {


    String username;
    List<Item> items;
    String orderNo;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public int getBill() {
        int amount = 0;
        for(int i=0; i<items.size(); i++) {
            amount += items.get(i).getPrice();
        }
        return amount;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = 15;
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(26) + 65);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public Order() {
        this.orderNo = random();
        this.status = "Pending";
    }

    public Order(String usr, List<Item> items) {
        this.username = usr;
        this.items = items;
        this.status = "Pending";
        this.orderNo = random();
    }

    public String getUsr() {
        return username;
    }


    public void setUsr(String usr) {
        this.username = usr;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
