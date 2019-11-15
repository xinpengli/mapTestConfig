package com.geekplus.maptest.Initialization;

import com.geekplus.maptest.Componet.API.WebSocket.WebSocketClientStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitRunner implements CommandLineRunner {
    /**Autowired 自动从bean容器中获取装载bean，required 默认为true当为 false时，代表当类没有被注入到容器时（即用@Bean,@componet注解的类）
     * 此时Autowired也不会报错
     * */
@Autowired(required = false)
WebSocketClientStart webSocketClientStart;

    @Override
    public void run(String... args) throws Exception {
//      webSocketClientStart.start();
    }
}
