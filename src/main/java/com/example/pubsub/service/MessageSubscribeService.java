package com.example.pubsub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class MessageSubscribeService {
    private final RedisMessageListenerContainer container;

    public synchronized SseEmitter subscribe(
            String category
    ) {
        SseEmitter emitter = new SseEmitter(0L);
        MessageSubscriber subscriber = new MessageSubscriber(emitter);
        ChannelTopic topic = new ChannelTopic("news:" + category);
        // 연결이 열리면 이 연결의 리스너로 news:{category} 채널을 구독
        container.addMessageListener(subscriber, topic);
        // 연결이 끊기면 구독 해제
        emitter.onCompletion(() -> container.removeMessageListener(subscriber, topic));
        return emitter;
    }
}
