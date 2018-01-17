package com.example.minas.bonus.client;


import java.util.List;

public class Client {

    Long id;

    String username;
    String password;
    String Email;
    String Phone;

    List<Bonus> bonuses;

    public Client() {

    }

    public Client(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        Email = email;
        Phone = phone;
    }
    public Client(String username, String password, String email, String phone, List<Bonus> bonuses) {
        this.username = username;
        this.password = password;
        Email = email;
        Phone = phone;
        this.bonuses = bonuses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public List<Bonus> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<Bonus> bonuses) {
        this.bonuses = bonuses;
    }

}
