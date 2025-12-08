package com.example.expensetracker.service;

import com.example.expensetracker.dto.DashboardSummary;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import com.example.expensetracker.model.Budget;
import com.example.expensetracker.dto.BudgetStatus;
import com.example.expensetracker.repository.BudgetRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository repo;

    private final BudgetRepository budgetRepo;

    public ExpenseService(ExpenseRepository repo, BudgetRepository budgetRepo) {
        this.repo = repo;
        this.budgetRepo = budgetRepo;
    }

    public Expense createExpense(Expense expense) {
        return repo.save(expense);
    }

    public Optional<Expense> getExpense(Long id) {
        return repo.findById(id);
    }

    public List<Expense> getAllExpenses() {
        return repo.findAll();
    }

    public Expense updateExpense(Long id, Expense updated) {
        return repo.findById(id).map(e -> {
            e.setTitle(updated.getTitle());
            e.setAmount(updated.getAmount());
            e.setDate(updated.getDate());
            e.setCategory(updated.getCategory());
            e.setNote(updated.getNote());
            return repo.save(e);
        }).orElseThrow(() -> new RuntimeException("Expense not found: " + id));
    }

    public void deleteExpense(Long id) {
        repo.deleteById(id);
    }

    public List<Expense> getByDateRange(LocalDate start, LocalDate end) {
        return repo.findByDateBetween(start, end);
    }

    public List<Expense> getByCategory(String category) {
        return repo.findByCategory(category);
    }

    public DashboardSummary getDashboardSummary(LocalDate start,
                                                LocalDate end,
                                                String category) {

        // 1. Load data based on filters
        List<Expense> expenses;

        boolean hasDateRange = (start != null && end != null);
        boolean hasCategory = (category != null && !category.isBlank());

        if (hasDateRange && hasCategory) {
            // date + category
            expenses = repo.findByDateBetween(start, end).stream()
                    .filter(e -> e.getCategory() != null &&
                            e.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        } else if (hasDateRange) {
            // only date
            expenses = repo.findByDateBetween(start, end);
        } else if (hasCategory) {
            // only category
            expenses = repo.findByCategory(category);
        } else {
            // no filters
            expenses = repo.findAll();
        }

        long recordCount = expenses.size();
        double totalAmount = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        Double averagePerExpense = (recordCount > 0)
                ? totalAmount / recordCount
                : null;

        // 2. Average per day (only if date range present and non-empty)
        Double averagePerDay = null;
        if (hasDateRange) {
            long days = ChronoUnit.DAYS.between(start, end) + 1;
            if (days > 0) {
                averagePerDay = totalAmount / days;
            }
        }

        // 3. Category totals
        Map<String, Double> categoryTotals = expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));

        List<BudgetStatus> budgetStatuses = categoryTotals.entrySet().stream()
                .map(entry -> {
                    String categoryName = entry.getKey();
                    double spent = entry.getValue();

                    return budgetRepo.findByCategoryIgnoreCase(categoryName)
                            .map(budget -> {
                                double limit = budget.getMonthlyLimit();
                                String status;

                                if (spent >= limit) {
                                    status = "EXCEEDED";
                                } else if (spent >= 0.8 * limit) {
                                    status = "WARNING";
                                } else {
                                    status = "SAFE";
                                }

                                return new BudgetStatus(categoryName, spent, limit, status);
                            })
                            .orElse(null);
                })
                .filter(b -> b != null)
                .toList();


        // 4. Top category
        String topCategory = categoryTotals.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        // 5. Build DTO
        DashboardSummary summary = new DashboardSummary();
        summary.setTotalAmount(totalAmount);
        summary.setRecordCount(recordCount);
        summary.setAveragePerExpense(averagePerExpense);
        summary.setAveragePerDay(averagePerDay);
        summary.setCategoryTotals(categoryTotals);
        summary.setTopCategory(topCategory);
        summary.setStartDate(start);
        summary.setEndDate(end);
        summary.setFilteredCategory(hasCategory ? category : null);

        summary.setBudgetStatuses(budgetStatuses);
        return summary;
    }
}
