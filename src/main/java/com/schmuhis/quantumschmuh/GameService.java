package com.schmuhis.quantumschmuh;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.schmuhis.quantumschmuh.MqttOutputConfiguration.*;

@Service
public class GameService {

    private final Logger log;
    private final DiceService diceService;
    private final MyGateway gateway;
    private final ControllerService controllerService;
    private int currentPlayer = 0;
    private final Map<Integer, PlayerData> data;
    private final ScheduledExecutorService executorService;

    public GameService(Logger log, DiceService diceService, MyGateway gateway, ControllerService controllerService) {
        this.log = log;
        this.diceService = diceService;
        this.gateway = gateway;
        this.data = new HashMap<>();
        this.data.put(0, new PlayerData(0, 0, 3));
        this.data.put(1, new PlayerData(0, 0, 3));
        this.data.put(2, new PlayerData(0, 0, 3));
        this.data.put(3, new PlayerData(0, 0, 3));
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.controllerService = controllerService;
    }

    public void updateSpot(int player) {
        var playerData = data.get(player);
        log.info("set players[{}] field {} to required {}", player, playerData.currentField, playerData.requiredField);
        playerData.currentField = playerData.requiredField;
        updateState();
    }

    private void updateState() {
        currentPlayer = (currentPlayer + 1) % 4;
        controllerService.requestDieRoll(currentPlayer);
    }

    public void dieRoll() {
        var playerData = data.get(currentPlayer);

        var value = diceService.getDieRoll();

        var prev = playerData.requiredField;

        if (playerData.requiredField == 0) {
            // the player is in the house
            playerData.requiredField = 5 * currentPlayer + value;
        } else {
            // the player is on the board
            playerData.requiredField += value;
        }

        playerData.requiredField %= 20;

        log.info("die roll of {} updated player[{}] to field {} from field {}", value, currentPlayer, playerData.requiredField, prev);

        executorService.schedule(() -> controllerService.sendRoll(value, currentPlayer), 2, TimeUnit.SECONDS);
    }

    static class PlayerData {
        public int requiredField;
        public int currentField;
        public int lives;

        public PlayerData(int requiredField, int currentField, int lives) {
            this.requiredField = requiredField;
            this.currentField = currentField;
            this.lives = lives;
        }
    }
}
