package com.schmuhis.quantumschmuh;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.concurrent.*;

@Service
public class DiceService {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public int getDieRoll() {
        RestClient client = RestClient.create();
        Result result = client.get().uri("https://lfdr.de/qrng_api/qrng?length=2").retrieve().body(Result.class);
        int value = Integer.parseInt(result.qrn, 16);
        return (value % 6) + 1;
    }

    public int dieRollFuture() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.getDieRoll();
    }

    record Result(int length, String qrn) {}
}
