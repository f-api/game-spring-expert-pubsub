package com.example.pubsub.service;

import com.example.pubsub.dto.ReceivedMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequiredArgsConstructor
public class MessageSubscriber implements MessageListener {
    private final SseEmitter emitter;

    @Override
    public void onMessage(
            Message message,
            byte[] pattern
    ) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("수신: channel={} message={}", channel, body);
        try {
            emitter.send(new ReceivedMessage(channel, body));
        } catch (IOException | IllegalStateException closed) {
            emitter.complete();
        }
    }
}
