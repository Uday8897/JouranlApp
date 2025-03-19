package com.example.Uday.JournalApp.Services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisServieTest {
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void redisTest(){
        redisTemplate.opsForValue().set("email","Devulaalli");
        Object email = redisTemplate.opsForValue().get("email");

    }
}
