package com.interview.user_management_service.service;

import com.interview.user_management_service.model.User;
import com.interview.user_management_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Any;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id){return userRepository.findById(id);}

    public User updateUser(Long id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean isAvailable(User user) {
        Optional<User> existingUser = userRepository.findByFirstNameAndLastName(user.getFirstName(), user.getLastName());
        return existingUser.isPresent();
    }

}
