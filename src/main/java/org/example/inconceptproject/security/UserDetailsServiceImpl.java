package org.example.inconceptproject.security;

import jakarta.transaction.Transactional;
import org.example.inconceptproject.model.auth.User;
import org.example.inconceptproject.repository.UserRepository;
import org.example.inconceptproject.security.model.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRole().name()));

        if (user.isHasTemporaryModeratorAuthority()
                && user.getModeratorAuthorityExpiresAt() != null
                && user.getModeratorAuthorityExpiresAt().isAfter(LocalDateTime.now())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
        }
        return new UserPrincipal(user);
    }
}
