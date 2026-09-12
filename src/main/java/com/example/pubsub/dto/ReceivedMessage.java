package com.example.pubsub.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReceivedMessage {
    private final String channel;
    private final String message;
}
