package com.example.splitit.database.data_models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.UUID;

@IgnoreExtraProperties
public class Expense {
    public String expenseId;
    public String expenseAmount;
    public String addedBy;

    public Expense(){

    }


    public Expense(String expenseAmount, String addedBy) {
        this.expenseId = UUID.randomUUID().toString();
        this.expenseAmount = expenseAmount;
        this.addedBy = addedBy;
    }
}
