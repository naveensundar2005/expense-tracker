package com.naveen.expense_tracker.service;

import com.naveen.expense_tracker.entity.User;
import com.naveen.expense_tracker.repository.ExpenseRepository;
import com.naveen.expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Map<String, Object> getSummary(String username) {
        User user = getUser(username);
        Double totalIncome = expenseRepository.sumAmountByUserAndType(user, "INCOME");
        Double totalExpense = expenseRepository.sumAmountByUserAndType(user, "EXPENSE");
        totalIncome = totalIncome != null ? totalIncome : 0.0;
        totalExpense = totalExpense != null ? totalExpense : 0.0;
        double balance = totalIncome - totalExpense;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("balance", balance);
        return summary;
    }

    public List<Object[]> getMonthlySummary(String username) {
        User user = getUser(username);
        return expenseRepository.findMonthlyTotalsByUser(user);
    }

    public List<Object[]> getTopCategories(String username) {
        User user = getUser(username);
        return expenseRepository.findTopCategoriesByUser(user);
    }
}