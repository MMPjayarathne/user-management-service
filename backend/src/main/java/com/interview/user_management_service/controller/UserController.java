package com.interview.user_management_service.controller;

import com.interview.user_management_service.model.User;
import com.interview.user_management_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid User user) {
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new ValidationException("A required body parameter is missing or invalid: firstName");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new ValidationException("A required body parameter is missing or invalid: lastName");
        }

        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user", e);
        }
    }


    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch users", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id).orElseThrow(() -> new ValidationException("User not found")));
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid User user) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, user));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }
}