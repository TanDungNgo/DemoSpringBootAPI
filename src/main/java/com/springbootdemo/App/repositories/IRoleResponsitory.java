package com.springbootdemo.App.repositories;

import com.springbootdemo.App.models.User.Role;
import com.springbootdemo.App.models.User.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleResponsitory extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
