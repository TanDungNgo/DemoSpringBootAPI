package com.springbootdemo.App.service;

import com.springbootdemo.App.models.User.Role;
import com.springbootdemo.App.models.User.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
