package com.example.community.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String userPassword;
    private String email;
    private String name;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Board> board;

    protected User() {
    }

    public User(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public User(String userId, String userPassword, String email, String name) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.name = name;
    }
}
