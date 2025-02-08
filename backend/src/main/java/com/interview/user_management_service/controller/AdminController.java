package com.interview.user_management_service.controller;

import com.interview.user_management_service.model.Admin;
import com.interview.user_management_service.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin) {
        if (admin.getUsername() == null || admin.getUsername().isEmpty()) {
            throw new ValidationException("A required body parameter is missing or invalid: username");
        }
        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
            throw new ValidationException("A required body parameter is missing or invalid: password");
        }
        if(adminService.isAvailable(admin)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The given admin username is already existing!");
        }else {
            try {
                adminService.registerAdmin(admin);
                return ResponseEntity.ok("Admin Registered Successfully");
            }catch (Exception e) {
                throw new RuntimeException("Failed to create admin", e);
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin admin) {
        boolean isAuthenticated = adminService.authenticateAdmin(admin.getUsername(), admin.getPassword());
        if (isAuthenticated) {
            log.info("Admin is Authenticated. Generating the token");
            String token = adminService.generateToken(admin);
            Map<String, String> res = new HashMap<>();
            res.put("token", token);
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(401).body("Invalid Username or Password");
        }
    }

}
