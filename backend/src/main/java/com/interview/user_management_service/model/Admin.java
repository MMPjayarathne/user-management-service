package com.interview.user_management_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * This model was only created for the user login through authentication and authorization,
 * Practically, in other scenarios, using the User with a role is recommended, but since the normal users do not have credential here.
 * Therefore, having separate Model for the admin users will reduce the confusion.
 * **/
@Entity
@Table(name = "Admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
}
