package com.example.expensetracker.dto;

public class BudgetStatus {

    private String category;
    private double spent;
    private double limit;
    private String status; // SAFE, WARNING, EXCEEDED

    public BudgetStatus(String category, double spent, double limit, String status) {
        this.category = category;
        this.spent = spent;
        this.limit = limit;
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public double getSpent() {
        return spent;
    }

    public double getLimit() {
        return limit;
    }

    public String getStatus() {
        return status;
    }
}
