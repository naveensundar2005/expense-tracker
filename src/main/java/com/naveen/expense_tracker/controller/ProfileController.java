package com.naveen.expense_tracker.controller;

import com.naveen.expense_tracker.dto.ProfileUpdateRequest;
import com.naveen.expense_tracker.dto.UserProfileResponse;
import com.naveen.expense_tracker.entity.User;
import com.naveen.expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(toResponse(user));
    }

    @PutMapping
    public ResponseEntity<UserProfileResponse> updateProfile(
            @RequestBody ProfileUpdateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getGender() != null) user.setGender(request.getGender());
        if (request.getOccupation() != null) user.setOccupation(request.getOccupation());
        if (request.getCity() != null) user.setCity(request.getCity());
        if (request.getAvatar() != null) user.setAvatar(request.getAvatar());
        if (request.getDateOfBirth() != null) user.setDateOfBirth(request.getDateOfBirth());

        userRepository.save(user);
        return ResponseEntity.ok(toResponse(user));
    }

    private UserProfileResponse toResponse(User user) {
        return new UserProfileResponse(
            user.getId(), user.getUsername(), user.getEmail(),
            user.getFullName(), user.getPhone(), user.getGender(),
            user.getOccupation(), user.getCity(), user.getAvatar(),
            user.getDateOfBirth(), user.getCreatedAt()
        );
    }
}
