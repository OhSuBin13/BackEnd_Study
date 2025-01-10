package com.example.community.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Board() {
    }

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
