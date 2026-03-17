package com.naveen.expense_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String gender;
    private String occupation;
    private String city;
    private String avatar;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
}
