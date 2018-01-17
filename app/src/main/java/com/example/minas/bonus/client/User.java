package com.example.minas.bonus.client;



public class User {
    private  String username;
    private  String phone ;
    private  String email;

    public User(){

    }
    public User(String username, String phone, String email) {
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
