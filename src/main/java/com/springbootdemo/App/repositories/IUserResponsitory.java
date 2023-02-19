package com.springbootdemo.App.repositories;

import com.springbootdemo.App.models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserResponsitory extends JpaRepository<User, Long> {
    // Tim kiem user co ton tai trong db k
    Optional<User> findByUsername(String username);
    // Username da co trong db chua
    Boolean existsByUsername(String username);
    // Email da co trong db chua
    Boolean existsByEmail(String email);
}
