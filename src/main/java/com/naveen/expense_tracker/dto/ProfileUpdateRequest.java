package com.naveen.expense_tracker.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProfileUpdateRequest {
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String occupation;
    private String city;
    private String avatar;
    private LocalDate dateOfBirth;
}
