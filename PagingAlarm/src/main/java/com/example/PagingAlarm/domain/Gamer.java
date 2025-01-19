package com.example.PagingAlarm.domain;

import com.example.PagingAlarm.domain.enumType.Rank;
import jakarta.persistence.*;

@Entity
public class Gamer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String age;

    @Column(name = "\"rank\"")
    private Rank rank;
}
