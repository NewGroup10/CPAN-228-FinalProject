package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.service;

import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.User;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user: " + username);
        
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                System.out.println("User not found: " + username);
                return new UsernameNotFoundException("User not found: " + username);
            });
            
        System.out.println("User found: " + username);
        System.out.println("Roles: " + user.getRoles());
        
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRoles().stream()
                .map(role -> role.getName().name())
                .toArray(String[]::new))
            .build();
    }
}
