package com.interview.user_management_service.service;

import com.interview.user_management_service.model.Admin;
import com.interview.user_management_service.model.User;
import com.interview.user_management_service.repository.AdminRepository;
import com.interview.user_management_service.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${admin.initial.username}")
    private String initialUsername;

    @Value("${admin.initial.password}")
    private String initialPassword;

    public AdminService(AdminRepository adminRepository, JwtTokenUtil jwtTokenUtil) {
        this.adminRepository = adminRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public Admin registerAdmin(Admin admin) {
        String salt = BCrypt.gensalt(12); // Especially use generated a salt :)
        String hashedPassword = hashPasswordWithSalt(admin.getPassword(), salt);
        admin.setPassword(hashedPassword);
        return adminRepository.save(admin);
    }

    public boolean authenticateAdmin(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            return checkPassword(password, admin.getPassword());
        }
        return false;
    }
    public String generateToken(Admin admin){
       return jwtTokenUtil.generateToken(admin.getUsername(), "ADMIN");
    }

    private String hashPasswordWithSalt(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }


    private boolean checkPassword(String password, String storedHash) {
        return BCrypt.checkpw(password, storedHash);
    }

    public Admin initializeAdmin() {
        if(adminRepository.findByUsername(initialUsername)==null) {
            Admin admin = new Admin();
            admin.setUsername(initialUsername);
            admin.setPassword(initialPassword);
            return registerAdmin(admin);
        }
        return null;
    }

    public boolean isAvailable(Admin admin) {
        Admin existingAdmin = adminRepository.findByUsername(admin.getUsername());
        if(existingAdmin != null){
            return true;
        }
        return false;
    }
}
