package com.ec.model;

import java.sql.Date;

public class Expenses {
    private int id;
    private int userId;
    private String description;
    private double amount;
    private String category;
    private Date expenseDate;

    public Expenses() {}

    // Constructor for adding a new expense
    public Expenses(int userId, String description, double amount, String category, Date expenseDate) {
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.expenseDate = expenseDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Date getExpenseDate() { return expenseDate; }
    public void setExpenseDate(Date expenseDate) { this.expenseDate = expenseDate; }
}