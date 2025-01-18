package com.example.Trip_Planner.domain.dto;

import com.example.Trip_Planner.domain.Rank;
import com.example.Trip_Planner.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserJoinForm {
    private String userId;
    private String password;

    public UserJoinForm(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public User toEntity(String encodedPassword){
        return User.builder()
                .userId(userId)
                .password(encodedPassword)
                .rank(Rank.USER)
                .build();
    }
}
