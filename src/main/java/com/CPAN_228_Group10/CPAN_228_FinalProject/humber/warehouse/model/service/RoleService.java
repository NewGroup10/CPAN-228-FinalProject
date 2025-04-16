package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.service;

import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.Role;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.RoleName;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.RoleRepository;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.User;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.Collections;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RoleService(RoleRepository roleRepository, 
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (roleRepository.findByName(RoleName.USER).isEmpty()) {
            Role userRole = new Role();
            userRole.setName(RoleName.USER);
            roleRepository.save(userRole);
        }
        if (roleRepository.findByName(RoleName.ADMIN).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName(RoleName.ADMIN);
            roleRepository.save(adminRole);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRoles(Collections.singleton(getAdminRole()));
            userRepository.save(adminUser);
            System.out.println("Default admin user created with username: admin and password: admin123");
        }
    }

    public Role getUserRole() {
        return roleRepository.findByName(RoleName.USER)
            .orElseThrow(() -> new RuntimeException("Default USER role not found"));
    }

    public Role getAdminRole() {
        return roleRepository.findByName(RoleName.ADMIN)
            .orElseThrow(() -> new RuntimeException("Default ADMIN role not found"));
    }
}
