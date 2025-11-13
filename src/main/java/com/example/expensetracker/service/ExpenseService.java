package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
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
}
