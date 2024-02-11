package com.back.BuyAhead.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "profile_image", nullable = false)
    private String profile_image;

    @Column(name = "greeting", nullable = false)
    private String greeting;

    @Builder
    public User(String email, String password, String name, String profile_image, String greeting) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.profile_image = profile_image;
        this.greeting = greeting;
    }
}