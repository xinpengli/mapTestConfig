package com.geekplus.maptest.Componet.API.WebSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

@Component
public   class WebSocketClientStart {
private  static Logger logger= LoggerFactory.getLogger(WebSocketClientStart.class);
    private static String uri = "ws://172.16.49.166:8890";
    public static Session session;
static{
    WebSocketContainer container = null;
    try {
        container = ContainerProvider.getWebSocketContainer();
    } catch (Exception ex) {
        System.out.println("error" + ex);
    }

    try {
        URI r = URI.create(uri);
        session = container.connectToServer(WebSocketClient.class, r);
    } catch (DeploymentException | IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

}
    public      void start() {
        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception ex) {
            System.out.println("error" + ex);
        }

        try {
            URI r = URI.create(uri);
            session = container.connectToServer(WebSocketClient.class, r);
        } catch (DeploymentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public  static void send(String info){
        try {
            WebSocketClientStart.session.getBasicRemote().sendText(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
