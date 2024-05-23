package com.schmuhis.quantumschmuh;

import com.schmuhis.quantumschmuh.MqttOutputConfiguration.MyGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final MyGateway gateway;

    @GetMapping
    public String test() {
        gateway.sendToMqtt("from test", new Random().nextBoolean() ? "topic1" : "topic2");
        return "Hello World";
    }
}
