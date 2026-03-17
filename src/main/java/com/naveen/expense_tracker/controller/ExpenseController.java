package com.naveen.expense_tracker.controller;

import com.naveen.expense_tracker.dto.ExpenseDTO;
import com.naveen.expense_tracker.entity.Expense;
import com.naveen.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> addExpense(
            @Valid @RequestBody ExpenseDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
            expenseService.addExpense(dto, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
            expenseService.getAllExpenses(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
            expenseService.getExpenseById(id, userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
            expenseService.updateExpense(id, dto, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        expenseService.deleteExpense(id, userDetails.getUsername());
        return ResponseEntity.ok("Expense deleted successfully");
    }
}