package com.shubham.mongoDB.Services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class  RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void TestSendMail(){
//        redisTemplate.opsForValue().set("email","shubham@gmail.com");
        Object email = redisTemplate.opsForValue().get("Mumbai");
        System.out.println(email);
        int a = 1;
    }

}
