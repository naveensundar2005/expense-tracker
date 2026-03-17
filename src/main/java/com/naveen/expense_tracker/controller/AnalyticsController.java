package com.naveen.expense_tracker.controller;

import com.naveen.expense_tracker.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
            analyticsService.getSummary(userDetails.getUsername()));
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<Object[]>> getMonthlySummary(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
            analyticsService.getMonthlySummary(userDetails.getUsername()));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Object[]>> getTopCategories(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
            analyticsService.getTopCategories(userDetails.getUsername()));
    }
}