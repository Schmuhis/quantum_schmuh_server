package com.schmuhis.quantumschmuh;

import com.schmuhis.quantumschmuh.MqttOutputConfiguration.MyGateway;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    private final PlayerService playerService;
    private final MyGateway gateway;
    private final ControllerService controllerService;
    private final DiceService diceService;

    public MainView(PlayerService playerService, ControllerService controllerService, MyGateway gateway, DiceService diceService) {
        this.controllerService = controllerService;
        this.playerService = playerService;
        this.gateway = gateway;
        this.diceService = diceService;

        var health = health();
        var kill = kill();
        var sounds = sounds();

        add(new HorizontalLayout(health, kill, sounds));

        var requestRoll = requestRoll();
        var setDice = setRoll();
        var setText = setText();

        add(new HorizontalLayout(requestRoll, setDice, setText));

        var firstScenario = first();
        var secondScenario = second();

        add(new HorizontalLayout(firstScenario, secondScenario));

    }

    private VerticalLayout second() {
        var button = new Button("Scenario Second");

        button.addClickListener(event -> executeSecond());

        return new VerticalLayout(button);
    }

    private void executeSecond() {

        playerService.setHealth(1, 1);

        try {
            playerService.playSound(1, "aua");
            Thread.sleep(2000);
            playerService.playSound(1, "das_wars");
            Thread.sleep(2000);
            playerService.die(1);
        } catch (Exception ignore) {}

    }

    private VerticalLayout first() {
        var button = new Button("Scenario First");

        button.addClickListener(event -> executeFirst());

        return new VerticalLayout(button);
    }

    private void executeFirst() {

        playerService.setHealth(1, 2);

        try {
            playerService.playSound(0, "runter");
            Thread.sleep(1500);
            playerService.setHealth(1, 1);
            Thread.sleep(1000);
            playerService.playSound(1, "aua");
        } catch (Exception ignore) {}

    }

    private VerticalLayout setText() {
        var text = new TextField("Text");
        var button = new Button("Set Text");
        button.addClickListener(event -> controllerService.displayText(text.getValue()));

        return new VerticalLayout(text, button);
    }

    private VerticalLayout setRoll() {
        var players = new Select<Integer>();
        players.setLabel("Player");
        players.setItems(1, 2, 3, 4);

        var button = new Button("Send Roll");
        int dieRoll = diceService.getDieRoll();
        button.addClickListener(event -> controllerService.sendRoll(dieRoll, players.getValue()));

        return new VerticalLayout(players, button);
    }

    private VerticalLayout requestRoll() {

        var players = new Select<Integer>();
        players.setLabel("Player");
        players.setItems(1, 2, 3, 4);

        var button = new Button("Request Roll");
        button.addClickListener(event -> controllerService.requestDieRoll(players.getValue() - 1));

        return new VerticalLayout(players, button);
    }

    private VerticalLayout health() {
        var players = new Select<Integer>();
        players.setLabel("Player");
        players.setItems(1, 2, 3, 4);

        var h1 = new Button("1");
        h1.addClickListener(event -> playerService.setHealth(players.getValue() - 1, 1));
        var h2 = new Button("2");
        h2.addClickListener(event -> playerService.setHealth(players.getValue() - 1, 2));
        var h3 = new Button("3");
        h3.addClickListener(event -> playerService.setHealth(players.getValue() - 1, 3));

        var horizontalLayout = new HorizontalLayout(h1, h2, h3);
        return new VerticalLayout(players, horizontalLayout);
    }

    private VerticalLayout kill() {
        var players = new Select<Integer>();
        players.setLabel("Player");
        players.setItems(1, 2, 3, 4);

        var button = new Button("Kill");
        button.addClickListener(event -> playerService.die(players.getValue() - 1));

        return new VerticalLayout(players, button);
    }

    private VerticalLayout sounds() {
        var sounds = new Select<String>();
        sounds.setLabel("Sound");
        sounds.setItems("aua", "omnom", "das_wars", "kaboom", "runter", "los_gehts");

        var players = new Select<Integer>();
        players.setLabel("Player");
        players.setItems(1, 2, 3, 4);

        var button = new Button("Play");
        button.addClickListener(event -> playerService.playSound(players.getValue() - 1, sounds.getValue()));

        var horizontalLayout = new HorizontalLayout(sounds, players);
        return new VerticalLayout(horizontalLayout, button);
    }
}
