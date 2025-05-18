package org.example.inconceptproject.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.dto.request.RegisterRequest;
import org.example.inconceptproject.enums.UserRoles;
import org.example.inconceptproject.model.auth.User;
import org.example.inconceptproject.model.auth.UserRole;
import org.example.inconceptproject.repository.UserRepository;
import org.example.inconceptproject.repository.UserRoleRepository;
import org.example.inconceptproject.security.JwtUtil;
import org.example.inconceptproject.security.model.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;


    public void registerJWT(RegisterRequest request, UserRoles role) {
        if (userRepository.existsByUsername((request.getUsername()))) {
            throw new RuntimeException("Email already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        UserRole defaultRole = roleRepository.findByName(role)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRole(defaultRole);

        userRepository.save(user);
    }

    public String refreshJWT(String refreshToken) {

        String username = jwtUtil.getUsername(refreshToken);
        UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(username);
        return jwtUtil.generateAccessToken(user);
    }

    public Map<String, String> loginJWT(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserPrincipal user = (UserPrincipal) auth.getPrincipal();
        return new HashMap<>(Map.of(
                "accessToken", jwtUtil.generateAccessToken(user),
                "refreshToken", jwtUtil.generateRefreshToken(user)));
    }
}
