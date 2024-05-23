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

    public void handle(Message<?> message) {
        var headers = message.getHeaders();
        var topic = headers.get("mqtt_receivedTopic");

        // server/1/method

        if (!(topic instanceof String s)) return;

        var topics = Arrays.stream(s.split("/")).toList();

        if (!topics.getFirst().equals("server")) {
            log.info("not a message for server {}", topics);
            return;
        }

        int player = Integer.parseInt(topics.get(1));


        System.out.println(topic);
    }
}
