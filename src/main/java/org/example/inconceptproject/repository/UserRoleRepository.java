package org.example.inconceptproject.repository;

import org.example.inconceptproject.enums.UserRoles;
import org.example.inconceptproject.model.auth.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("SELECT ur FROM UserRole ur WHERE ur.role = :role")
    Optional<UserRole> findByName(UserRoles role);
}