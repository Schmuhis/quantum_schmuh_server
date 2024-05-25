package com.schmuhis.quantumschmuh;

import com.schmuhis.quantumschmuh.MqttOutputConfiguration.MyGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final MyGateway gateway;

    private final DiceService diceService;

    private final PlayerService playerService;
    private final ControllerService controllerService;

//    @GetMapping
//    public String test() {
////        playerService.setHealth(2, 2);
////        playerService.playSound(1, "schwenglon");
////        playerService.die(3);
//        gateway.sendToMqtt("schwenlgon", "die");
//        gateway.sendToMqtt("schwenlgon", "health");
//        gateway.sendToMqtt("schwenlgon", "sound");
//        return "Hello World";
//    }

    @EventListener(ContextRefreshedEvent.class)
    public void onStart() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            controllerService.displayText("INCREDIBILIS");
        }
    }
}
