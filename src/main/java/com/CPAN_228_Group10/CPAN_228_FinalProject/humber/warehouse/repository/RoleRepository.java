package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository;

import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.Role;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
