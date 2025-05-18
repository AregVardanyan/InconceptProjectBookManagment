package org.example.inconceptproject.repository;

import org.example.inconceptproject.model.AuthorRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRoleRepository extends JpaRepository<AuthorRole, Long> {
    Optional<AuthorRole> findByName(String name);

}
