package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTemplateTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void testRedis(){
        String name=stringRedisTemplate.opsForValue().get("name");
        int m=100;
    }
}
