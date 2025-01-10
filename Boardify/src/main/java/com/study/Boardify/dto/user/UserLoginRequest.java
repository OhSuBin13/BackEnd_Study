package com.study.Boardify.dto.user;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String loginId;
    private String password;
}
