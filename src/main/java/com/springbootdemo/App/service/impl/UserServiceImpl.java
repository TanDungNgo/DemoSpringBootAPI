package com.springbootdemo.App.service.impl;

import com.springbootdemo.App.models.User.User;
import com.springbootdemo.App.repositories.IUserResponsitory;
import com.springbootdemo.App.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserResponsitory userResponsitory;
    @Override
    public Optional<User> findByUsername(String name) {
        return userResponsitory.findByUsername(name);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userResponsitory.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userResponsitory.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userResponsitory.save(user);
    }
}
