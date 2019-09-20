package com.geekplus.maptest.Initialization;

import com.geekplus.maptest.Componet.API.WebSocket.WebSocketClientStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitRunner implements CommandLineRunner {
@Autowired
WebSocketClientStart webSocketClientStart;

    @Override
    public void run(String... args) throws Exception {
      /*  webSocketClientStart.start();*/
    }
}
