package org.example.inconceptproject.model.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.inconceptproject.enums.UserRoles;


@Entity
@Table(name = "user_role")
@Getter
@Setter
@RequiredArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Enumerated(EnumType.STRING)
    private UserRoles role;
}
