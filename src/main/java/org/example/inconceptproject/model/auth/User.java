package org.example.inconceptproject.model.auth;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "app_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id")
    private UserRole role;

    private boolean hasTemporaryModeratorAuthority;

    private LocalDateTime moderatorAuthorityExpiresAt;
}
