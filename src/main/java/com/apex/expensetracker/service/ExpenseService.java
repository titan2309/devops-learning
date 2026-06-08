package com.apex.expensetracker.service;

import com.apex.expensetracker.entity.Expense;
import com.apex.expensetracker.exception.ResourceNotFoundException;
import com.apex.expensetracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense not found with id: " + id));
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {

        Expense existingExpense = getExpenseById(id);

        existingExpense.setTitle(updatedExpense.getTitle());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setExpenseDate(updatedExpense.getExpenseDate());

        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(Long id) {

        Expense expense = getExpenseById(id);

        expenseRepository.delete(expense);
    }
}