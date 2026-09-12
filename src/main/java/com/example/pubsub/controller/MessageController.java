package com.example.pubsub.controller;

import com.example.pubsub.dto.MessagePublishRequest;
import com.example.pubsub.service.MessagePublishService;
import com.example.pubsub.service.MessageSubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessagePublishService messagePublishService;
    private final MessageSubscribeService messageSubscribeService;

    @PostMapping("/news/{category}/messages")
    public ResponseEntity<Void> publish(
            @PathVariable String category,
            @RequestBody MessagePublishRequest request
    ) {
        messagePublishService.publish(category, request.getMessage());
        return ResponseEntity.ok().build();
    }

    // 브라우저가 연결하면 news:{category} 채널을 구독하고, 받은 메시지를 이 연결로 보냄
    @GetMapping("/news/{category}/stream")
    public SseEmitter stream(
            @PathVariable String category
    ) {
        return messageSubscribeService.subscribe(category);
    }
}
