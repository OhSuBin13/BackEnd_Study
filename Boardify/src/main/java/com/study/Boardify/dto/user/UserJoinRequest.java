package com.study.Boardify.dto.user;

import com.study.Boardify.domain.User;
import com.study.Boardify.domain.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserJoinRequest {
    private String loginId;
    private String password;
    private String passwordCheck;
    private String nickname;

    public User toEntity(String encodedPassword){
        return User.builder()
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .userRole(UserRole.BRONZE)
                .createdAt(LocalDateTime.now())
                .receivedLikeCnt(0)
                .build();
    }
}
