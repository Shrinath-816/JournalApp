package net.engineeringdigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/redis-test")
    public String testRedis(){
        stringRedisTemplate.opsForValue().set("ping","pong");
        return stringRedisTemplate.opsForValue().get("ping");
    }

}
