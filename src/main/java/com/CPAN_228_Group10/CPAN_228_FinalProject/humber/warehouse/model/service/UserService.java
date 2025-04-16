package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.service;

import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.User;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder,
                      RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    public boolean registerUser(User user) {
        try {
            System.out.println("Starting user registration process for: " + user.getUsername());
            
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                System.out.println("User already exists: " + user.getUsername());
                return false;
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            user.setRoles(Collections.singleton(roleService.getUserRole()));
            
            User savedUser = userRepository.save(user);
            System.out.println("User saved successfully with ID: " + savedUser.getId());
            return true;
        } catch (Exception e) {
            System.err.println("Error during user registration: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public boolean registerAdmin(User user) {
        try {
            System.out.println("Starting admin user registration process for: " + user.getUsername());
            
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                System.out.println("User already exists: " + user.getUsername());
                return false;
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(roleService.getAdminRole()));
            
            User savedUser = userRepository.save(user);
            System.out.println("Admin user saved successfully with ID: " + savedUser.getId());
            return true;
        } catch (Exception e) {
            System.err.println("Error during admin user registration: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
