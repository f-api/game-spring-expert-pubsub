package com.example.pubsub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagePublishService {
    private final StringRedisTemplate redisTemplate;

    public void publish(
            String category,
            String message
    ) {
        String channel = "news:" + category;
        redisTemplate.convertAndSend(channel, message);
    }
}
