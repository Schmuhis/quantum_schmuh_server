package com.schmuhis.quantumschmuh;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class MsgHandler {

    private final Logger log;

    private final GameService gameService;

    public void handle(Message<?> message) {
        var headers = message.getHeaders();
        var topic = headers.get("mqtt_receivedTopic");

        log.info("message payload {}", message.getPayload());

        if (!(topic instanceof String s)) return;

        var topics = Arrays.stream(s.split("/")).toList();

        if (!topics.getFirst().equals("server")) {
            log.info("not a message for server {}", topic);
            return;
        }

        int player;
        try {
            player = Integer.parseInt(topics.get(1));
        } catch (NumberFormatException e) {
            log.info("can not determine player from topic: {}", topic);
            return;
        }

        switch (topics.get(2)) {
            case "update-spot" -> gameService.updateSpot(player);
            case "rolli" -> gameService.dieRoll();
            case String command -> log.warn("unknown command: {}", command);
        }
    }
}
