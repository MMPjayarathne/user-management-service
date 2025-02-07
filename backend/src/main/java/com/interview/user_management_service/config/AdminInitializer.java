package com.interview.user_management_service.config;
import com.interview.user_management_service.model.Admin;
import com.interview.user_management_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final AdminService adminService;

    @Autowired
    public AdminInitializer(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize admin user on startup if not exists
        Admin admin = adminService.initializeAdmin();
        if (admin != null) {
            System.out.println("Admin user initialized: " + admin.getUsername());
        }
    }
}
