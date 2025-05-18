package org.example.inconceptproject.security;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.dto.request.RegisterRequest;
import org.example.inconceptproject.enums.UserRoles;
import org.example.inconceptproject.model.auth.User;
import org.example.inconceptproject.model.auth.UserRole;
import org.example.inconceptproject.repository.UserRepository;
import org.example.inconceptproject.repository.UserRoleRepository;
import org.example.inconceptproject.service.auth.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Bean
    public CommandLineRunner insertRolesAndCreateAdmin(UserRoleRepository userRoleRepository, UserService userService) {
        return args -> {
            for (UserRoles userRole : UserRoles.values()) {
                    UserRole role = new UserRole();
                    role.setRole(userRole);
                    userRoleRepository.save(role);

            }
            createAdmin(userService);
        };
    }

    public void createAdmin(UserService userService) {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUsername("admin");
            registerRequest.setPassword("1234");

            userService.registerJWT(registerRequest, UserRoles.ROLE_ADMIN);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(
                        a ->
                                a.requestMatchers("/auth/**").permitAll()
                                        .requestMatchers(
                                                "/v3/api-docs/**",
                                                "/swagger-ui/**",
                                                "/swagger-ui.html"
                                        ).permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                .authenticationProvider(this.authenticationProvider())
                .addFilterBefore(this.authorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter authorizationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);

        return authenticationProvider;
    }
}
