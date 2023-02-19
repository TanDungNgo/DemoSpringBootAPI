package com.springbootdemo.App.controllers;

import com.springbootdemo.App.dto.request.SignInForm;
import com.springbootdemo.App.dto.request.SignUpForm;
import com.springbootdemo.App.dto.response.JwtResponse;
import com.springbootdemo.App.dto.response.ResponMessage;
import com.springbootdemo.App.models.User.Role;
import com.springbootdemo.App.models.User.RoleName;
import com.springbootdemo.App.models.User.User;
import com.springbootdemo.App.security.jwt.JwtProvider;
import com.springbootdemo.App.security.userprincal.UserPrinciple;
import com.springbootdemo.App.service.impl.RoleServiceImpl;
import com.springbootdemo.App.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponMessage("The username existed! Please try again!"), HttpStatus.OK);
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponMessage("The email existed! Please try again!"), HttpStatus.OK);
        }
        User user = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()), signUpForm.getAvatar());
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "ADMIN":
                    Role adminRole = roleService.findByName(RoleName.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Role not found!"));
                    roles.add(adminRole);
                    break;
                case "TEACHER":
                    Role teacherRole = roleService.findByName(RoleName.TEACHER)
                            .orElseThrow(() -> new RuntimeException("Role not found!"));
                    roles.add(teacherRole);
                    break;
                default:
                    Role studentRole = roleService.findByName(RoleName.STUDENT)
                            .orElseThrow(() -> new RuntimeException("Role not found!"));
                    roles.add(studentRole);
                    break;
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponMessage("Signup success!"), HttpStatus.OK);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return new ResponseEntity<>(new JwtResponse(userPrinciple.getId(),token, userPrinciple.getName(),userPrinciple.getAvatar(), userPrinciple.getAuthorities()), HttpStatus.OK);
    }
}
