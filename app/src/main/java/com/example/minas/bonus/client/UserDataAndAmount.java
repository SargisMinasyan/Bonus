package com.example.minas.bonus.client;

import java.util.Date;



class UserDataAndAmount {
    private String date;
    private String amount;

    public UserDataAndAmount(String date, String amount) {
        this.date = date;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
