package org.example.inconceptproject.repository;

import org.example.inconceptproject.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
           SELECT u FROM User u WHERE u.username = :username
        """)
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
