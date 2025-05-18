package org.example.inconceptproject.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.enums.UserRoles;
import org.example.inconceptproject.model.auth.User;
import org.example.inconceptproject.model.auth.UserRole;
import org.example.inconceptproject.repository.UserRepository;
import org.example.inconceptproject.repository.UserRoleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public boolean grantTemporaryModeratorPermission(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();
        user.setHasTemporaryModeratorAuthority(true);
        user.setModeratorAuthorityExpiresAt(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
        userRepository.save(user);
        return true;
    }

    public boolean assignRole(String username, UserRoles role) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        Optional<UserRole> optionalRole = userRoleRepository.findByName(role);
        System.out.println(optionalUser.isPresent());
        System.out.println(optionalRole.isPresent());
        if (optionalUser.isPresent() && optionalRole.isPresent()) {
            User user = optionalUser.get();
            UserRole userRole = optionalRole.get();
            user.setRole(userRole);
            userRepository.save(user);
            return true;
        }

        return false;
    }


}

