package com.geekplus.maptest.Componet.API.WebSocket;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.TimerTask;


@Getter
/**WebSocketClientStart该类属性都是静态类变量，WebSocketClientStart它又作为配置类用@ConfigurationProperties从系统配置赋值属性值，
 * ConfigurationProperties只能调用该类的非静态setter方法，所以要手动写各个属性的seeter方法，因为默认的lomnok @setter是根据属性的修饰变量修饰，例如以下
 * public static String ip; 那么对应的@setter方法也是静态的
 * */
public class WebSocketClientStart {
    private static Logger logger = LoggerFactory.getLogger(WebSocketClientStart.class);
    public static String ip;
    public static String port;
    public static String uri;
    public static Session session;

    /*static{
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

    }*/
    public static void start() {
        WebSocketContainer container = null;
        WebSocketClientStart.uri = "ws://" + WebSocketClientStart.ip + ":" + WebSocketClientStart.port;
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
/**
 * @PostConstruct注解的方法（必须是非静态方法）会在他初始化作为bean后才执行
 * 在本例中就是WebSocketClientStart作为配置类，用@beann标注 new WebSocketClientStart 方法返回WebSocketClientStart对象并且它的属性被@ConfigurationProperties
 * 赋值后才会调用，所以不会抛出属性为空的空指针错误
 * */
    @PostConstruct
    private void init() {
         WebSocketClientStart.start();
    }
/**
 * @
 * */

    public  void setIp(String ip) {
        WebSocketClientStart.ip = ip;
    }

    public  void setPort(String port) {
        WebSocketClientStart.port = port;
    }

    public  void setUri(String uri) {
        WebSocketClientStart.uri = uri;
    }

    public  void setSession(Session session) {
        WebSocketClientStart.session = session;
    }

    public void send(String info) {
        try {
            this.session.getBasicRemote().sendText(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
