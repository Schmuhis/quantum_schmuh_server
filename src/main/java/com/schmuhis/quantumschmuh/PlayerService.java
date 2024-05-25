package com.schmuhis.quantumschmuh;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final MqttOutputConfiguration.MyGateway gateway;

    private final Logger log;

    public void setHealth(int player, int health) {
        log.info("setting health of player [{}] to {}", player ,health);
        gateway.sendToMqtt(String.valueOf(health),"player/" + player + "/health");
    }

    public void playSound(int player, String sound) {
        log.info("playing sound {} for player[{}]", sound, player);
        gateway.sendToMqtt(sound, "player/" + player + "/sound");
    }

    public void die(int player) {
        log.info("sending player [{}] to the gulag", player);
        gateway.sendToMqtt("die", "player/" + player + "/die");
    }
}
