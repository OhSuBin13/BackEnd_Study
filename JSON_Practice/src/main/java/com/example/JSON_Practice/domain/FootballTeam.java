package com.example.JSON_Practice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class FootballTeam {
    private String teamName;
    private String league;
    private Integer ranking;
    private ArrayList<Player> players;
}
