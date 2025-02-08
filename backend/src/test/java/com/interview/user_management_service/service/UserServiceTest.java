package com.interview.user_management_service.service;

import com.interview.user_management_service.model.User;
import com.interview.user_management_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Masith");
        user.setLastName("Pramuditha");
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("Masith", createdUser.getFirstName());
        assertEquals("Pramuditha", createdUser.getLastName());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("Masith", users.get(0).getFirstName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUser(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("Masith", foundUser.get().getFirstName());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setFirstName("Kasun");
        updatedUser.setLastName("Perera");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("Kasun", result.getFirstName());
        assertEquals("Perera", result.getLastName());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, user));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testIsAvailable() {
        when(userRepository.findByFirstNameAndLastName("Masith", "Pramuditha")).thenReturn(Optional.of(user));

        boolean isAvailable = userService.isAvailable(user);

        assertTrue(isAvailable);

        verify(userRepository, times(1)).findByFirstNameAndLastName("Masith", "Pramuditha");
    }

    @Test
    void testIsAvailable_UserNotAvailable() {
        when(userRepository.findByFirstNameAndLastName("Masith", "Pramuditha")).thenReturn(Optional.empty());

        boolean isAvailable = userService.isAvailable(user);

        assertFalse(isAvailable);

        verify(userRepository, times(1)).findByFirstNameAndLastName("Masith", "Pramuditha");
    }
}