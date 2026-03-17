package com.naveen.expense_tracker.service;

import com.naveen.expense_tracker.dto.ExpenseDTO;
import com.naveen.expense_tracker.entity.Category;
import com.naveen.expense_tracker.entity.Expense;
import com.naveen.expense_tracker.entity.User;
import com.naveen.expense_tracker.repository.CategoryRepository;
import com.naveen.expense_tracker.repository.ExpenseRepository;
import com.naveen.expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Expense addExpense(ExpenseDTO dto, String username) {
        User user = getUser(username);
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setAmount(dto.getAmount());
        expense.setType(dto.getType());
        expense.setDescription(dto.getDescription());
        expense.setExpenseDate(dto.getExpenseDate());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            expense.setCategory(category);
        }

        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses(String username) {
        User user = getUser(username);
        return expenseRepository.findByUserOrderByExpenseDateDesc(user);
    }

    public Expense getExpenseById(Long id, String username) {
        User user = getUser(username);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        return expense;
    }

    public Expense updateExpense(Long id, ExpenseDTO dto, String username) {
        Expense expense = getExpenseById(id, username);
        expense.setAmount(dto.getAmount());
        expense.setType(dto.getType());
        expense.setDescription(dto.getDescription());
        expense.setExpenseDate(dto.getExpenseDate());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            expense.setCategory(category);
        }

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id, String username) {
        Expense expense = getExpenseById(id, username);
        expenseRepository.delete(expense);
    }
}