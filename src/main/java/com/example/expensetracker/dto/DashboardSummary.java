package com.example.expensetracker.dto;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;

public class DashboardSummary {
    private double totalAmount;
    private long recordCount;
    private Double averagePerExpense;  // nullable
    private Double averagePerDay;      // nullable
    private String topCategory;        // nullable if no data
    private Map<String, Double> categoryTotals;
    private List<BudgetStatus> budgetStatuses;
    private LocalDate startDate;
    private LocalDate endDate;
    private String filteredCategory;   // nullable

    public DashboardSummary() {
    }

    public DashboardSummary(double totalAmount,
                            long recordCount,
                            Double averagePerExpense,
                            Double averagePerDay,
                            String topCategory,
                            Map<String, Double> categoryTotals,
                            LocalDate startDate,
                            LocalDate endDate,
                            String filteredCategory) {
        this.totalAmount = totalAmount;
        this.recordCount = recordCount;
        this.averagePerExpense = averagePerExpense;
        this.averagePerDay = averagePerDay;
        this.topCategory = topCategory;
        this.categoryTotals = categoryTotals;
        this.startDate = startDate;
        this.endDate = endDate;
        this.filteredCategory = filteredCategory;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public Double getAveragePerExpense() {
        return averagePerExpense;
    }

    public void setAveragePerExpense(Double averagePerExpense) {
        this.averagePerExpense = averagePerExpense;
    }

    public Double getAveragePerDay() {
        return averagePerDay;
    }

    public void setAveragePerDay(Double averagePerDay) {
        this.averagePerDay = averagePerDay;
    }

    public String getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(String topCategory) {
        this.topCategory = topCategory;
    }

    public Map<String, Double> getCategoryTotals() {
        return categoryTotals;
    }

    public void setCategoryTotals(Map<String, Double> categoryTotals) {
        this.categoryTotals = categoryTotals;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getFilteredCategory() {
        return filteredCategory;
    }

    public void setFilteredCategory(String filteredCategory) {
        this.filteredCategory = filteredCategory;
    }
    public List<BudgetStatus> getBudgetStatuses() {
        return budgetStatuses;
    }

    public void setBudgetStatuses(List<BudgetStatus> budgetStatuses) {
        this.budgetStatuses = budgetStatuses;
    }

}

