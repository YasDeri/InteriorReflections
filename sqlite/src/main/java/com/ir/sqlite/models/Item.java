package com.ir.sqlite.models;

import java.io.Serializable;

public class Item implements Serializable {
    String name, category;
    int price;
    int img;
    int ID;
    String imgUri;
    String modelUri;
    String vendor;


    public String getVendor() {
        return vendor;
    }

    public Item(String name, String category, int price, int img, int ID, String imgUri, String vendor) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.img = img;
        this.ID = ID;
        this.imgUri = imgUri;
        this.vendor = vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getModelUri() { return modelUri; }

    public void setModelUri(String uri) { this.modelUri = uri; }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Item() {
    }

    public Item(String name, int price, int img, String category) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.category = category;
    }

    public Item(String name, int price, String category) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.category = category;
    }

    public Item(String name, int price, String category, String uri, String vendor) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.category = category;
        this.imgUri = uri;
        this.vendor = vendor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
