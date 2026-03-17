package com.naveen.expense_tracker.repository;

import com.naveen.expense_tracker.entity.Expense;
import com.naveen.expense_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserOrderByExpenseDateDesc(User user);

    List<Expense> findByUserAndTypeOrderByExpenseDateDesc(User user, String type);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user AND e.type = :type")
    Double sumAmountByUserAndType(@Param("user") User user, @Param("type") String type);

    @Query("SELECT e.category.name, SUM(e.amount) FROM Expense e WHERE e.user = :user AND e.type = 'EXPENSE' GROUP BY e.category.name ORDER BY SUM(e.amount) DESC")
    List<Object[]> findTopCategoriesByUser(@Param("user") User user);

    @Query("SELECT MONTH(e.expenseDate), SUM(e.amount), e.type FROM Expense e WHERE e.user = :user GROUP BY MONTH(e.expenseDate), e.type ORDER BY MONTH(e.expenseDate)")
    List<Object[]> findMonthlyTotalsByUser(@Param("user") User user);
}