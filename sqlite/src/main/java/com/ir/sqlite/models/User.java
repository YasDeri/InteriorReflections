package com.ir.sqlite.models;

public class User {
    private String fname, lname, address, username, password, type;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    void User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;

    }

    public String getUsername() {
        return username;
    }

    public User(String fname, String lname, String address, String username, String password, String type) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }
}

