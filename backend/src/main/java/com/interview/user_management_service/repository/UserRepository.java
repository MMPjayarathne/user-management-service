package com.interview.user_management_service.repository;


import com.interview.user_management_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
}