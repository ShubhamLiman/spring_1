package com.shubham.mongoDB.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object json = redisTemplate.opsForValue().get(key);
            if (json == null) return null;

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json.toString(), entityClass);
        } catch (Exception e) {
            log.error("Error while reading from Redis", e);
            return null;
        }
    }

    public void set(String key, Object o, long ttl){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,json,ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception ",e);
        }
    }
}
