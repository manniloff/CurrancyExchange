package com.orange.user.model;

import com.orange.user.util.Roles;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private int id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    private boolean active;

    @Column(name = "role", nullable = false,
            columnDefinition = "ENUM('ADMIN', 'USER')")
    @Enumerated(value = EnumType.STRING)
    private Roles roles;
}
