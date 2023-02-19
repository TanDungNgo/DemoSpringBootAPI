package com.springbootdemo.App.service.impl;

import com.springbootdemo.App.models.User.Role;
import com.springbootdemo.App.models.User.RoleName;
import com.springbootdemo.App.repositories.IRoleResponsitory;
import com.springbootdemo.App.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    IRoleResponsitory roleResponsitory;
    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleResponsitory.findByName(name);
    }
}
