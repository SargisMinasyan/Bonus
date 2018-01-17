package com.example.minas.bonus.client;


public class Bonus {


    Long id;
    String data;
    String amount;



    public Bonus(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bonus(String data, String amount) {
        this.data = data;
        this.amount = amount;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
