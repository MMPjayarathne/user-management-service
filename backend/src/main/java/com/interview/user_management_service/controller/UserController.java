package com.interview.user_management_service.controller;

import com.interview.user_management_service.model.User;
import com.interview.user_management_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if(user.getFirstName() == null){
            return ResponseEntity.badRequest().body("A required body parameter is missing: firstName");
        }else if(user.getLastName() == null){
            return ResponseEntity.badRequest().body("A required body parameter is missing: lastName");
        }
        if(user.getFirstName().isEmpty() || user.getLastName().isEmpty()){
            return ResponseEntity.badRequest().body("Both firstName or lastName cannot be null!");
        }
        try{
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
