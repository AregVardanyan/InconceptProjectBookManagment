package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.dto.request.AuthRequest;
import org.example.inconceptproject.dto.request.RegisterRequest;
import org.example.inconceptproject.enums.UserRoles;
import org.example.inconceptproject.security.JwtUtil;
import org.example.inconceptproject.service.auth.PermissionService;
import org.example.inconceptproject.service.auth.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PermissionService permissionService;

    /**
     * Authenticates a user with a username and password and returns JWT tokens.
     *
     * @param request AuthRequest object containing username and password
     * @return accessToken and refreshToken if authentication succeeds
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Map<String,String> map = userService.loginJWT(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(map);
    }

    /**
     * Refreshes the access token using a valid refresh token.
     *
     * @param body A map containing the refreshToken
     * @return new accessToken if the refresh token is valid
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (!jwtUtil.isVerified(refreshToken)) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
        return ResponseEntity.ok(Map.of(
                "accessToken", userService.refreshJWT(refreshToken)
        ));
    }

    /**
     * Registers a new user with default role READER.
     *
     * @param request RegisterRequest containing user registration data
     * @return success message upon registration
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.registerJWT(request, UserRoles.ROLE_READER);
        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * Assigns a specific role to a user (Admin only).
     *
     * @param username the username of the target user
     * @param role     the role to assign
     * @return success or failure message
     */
    @PostMapping("/assign-role")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> assignRoleToUser(
            @RequestParam String username,
            @RequestParam UserRoles role
    ) {
        boolean result = permissionService.assignRole(username, role);
        if (result) {
            return ResponseEntity.ok("Role " + role + " assigned to " + username);
        } else {
            return ResponseEntity.badRequest().body("Failed to assign role");
        }
    }

    /**
     * Grants temporary moderator permission to a user for 1 day (Admin only).
     *
     * @param username the username to grant permission to
     * @return success or failure message
     */
    @PostMapping("/grant-temp-moderator")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> grantTemporaryModerator(
            @RequestParam String username) {
        boolean granted = permissionService.grantTemporaryModeratorPermission(username);
        if (granted) {
            return ResponseEntity.ok("Granted temporary moderator permission to " + username + " for 1 day.");
        } else {
            return ResponseEntity.badRequest().body("Failed to grant temporary permission.");
        }
    }
}
