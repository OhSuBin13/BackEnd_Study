package com.example.community.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @Test
    void getter(){
        User user = new User("Subin", "1234");
        String findMemberId = user.getUserId();

        assertThat("Subin").isEqualTo(findMemberId);
        assertThat("1234").isEqualTo(user.getUserPassword());
    }

}