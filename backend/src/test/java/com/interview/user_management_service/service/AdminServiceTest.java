package com.interview.user_management_service.service;

import com.interview.user_management_service.model.Admin;
import com.interview.user_management_service.repository.AdminRepository;
import com.interview.user_management_service.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AdminService adminService;

    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("password");
    }

    @Test
    void testRegisterAdmin() {
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin registeredAdmin = adminService.registerAdmin(admin);

        assertNotNull(registeredAdmin);
        assertEquals("admin", registeredAdmin.getUsername());
        assertTrue(registeredAdmin.getPassword().startsWith("$2a$12$")); // Check if password is hashed

        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    void testAuthenticateAdmin_Success() {
        String hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt(12));
        admin.setPassword(hashedPassword);

        when(adminRepository.findByUsername("admin")).thenReturn(admin);

        boolean isAuthenticated = adminService.authenticateAdmin("admin", "password");

        assertTrue(isAuthenticated);
        verify(adminRepository, times(1)).findByUsername("admin");
    }

    @Test
    void testAuthenticateAdmin_Failure() {
        when(adminRepository.findByUsername("admin")).thenReturn(null);

        boolean isAuthenticated = adminService.authenticateAdmin("admin", "wrongpassword");

        assertFalse(isAuthenticated);
        verify(adminRepository, times(1)).findByUsername("admin");
    }

    @Test
    void testGenerateToken() {
        when(jwtTokenUtil.generateToken("admin", "ADMIN")).thenReturn("token");

        String token = adminService.generateToken(admin);

        assertNotNull(token);
        assertEquals("token", token);
        verify(jwtTokenUtil, times(1)).generateToken("admin", "ADMIN");
    }

    @Test
    void testInitializeAdmin_WhenAdminDoesNotExist() {
        // Manually set the initialUsername and initialPassword fields
        ReflectionTestUtils.setField(adminService, "initialUsername", "initialAdmin");
        ReflectionTestUtils.setField(adminService, "initialPassword", "initialPassword");

        when(adminRepository.findByUsername("initialAdmin")).thenReturn(null);
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin initializedAdmin = adminService.initializeAdmin();

        assertNotNull(initializedAdmin);
        assertEquals("admin", initializedAdmin.getUsername());
        verify(adminRepository, times(1)).findByUsername("initialAdmin");
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    void testInitializeAdmin_WhenAdminAlreadyExists() {
        // Manually set the initialUsername and initialPassword fields
        ReflectionTestUtils.setField(adminService, "initialUsername", "initialAdmin");
        ReflectionTestUtils.setField(adminService, "initialPassword", "initialPassword");

        when(adminRepository.findByUsername("initialAdmin")).thenReturn(admin);

        Admin initializedAdmin = adminService.initializeAdmin();

        assertNull(initializedAdmin);
        verify(adminRepository, times(1)).findByUsername("initialAdmin");
        verify(adminRepository, never()).save(any(Admin.class));
    }

}