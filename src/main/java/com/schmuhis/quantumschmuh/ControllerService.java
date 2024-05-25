package com.schmuhis.quantumschmuh;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ControllerService {

    private final MqttOutputConfiguration.MyGateway gateway;

    private final Logger log;

    public void requestDieRoll(int player) {
        log.info("requesting die roll for player [{}]", player);
        gateway.sendToMqtt(String.valueOf(player),"controller/roll");
    }

    public void sendRoll(int value, int player) {
        log.info("sending roll [{}]", value);
        gateway.sendToMqtt(player + ";" + value, "controller/dice");
    }

    public void blank() {
        log.info("sending blank to controller");
        gateway.sendToMqtt("","controller/text");
    }

    public void displayText(String text) {
        log.info("sending text: {} to controller", text);
        gateway.sendToMqtt(text,"controller/text");
    }
}
