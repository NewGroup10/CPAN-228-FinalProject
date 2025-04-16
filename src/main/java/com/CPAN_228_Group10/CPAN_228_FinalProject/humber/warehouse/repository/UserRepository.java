package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository;

import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
