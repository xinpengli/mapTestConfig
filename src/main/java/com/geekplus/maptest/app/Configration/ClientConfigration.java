package com.geekplus.maptest.app.Configration;

import com.geekplus.maptest.Componet.API.WebSocket.WebSocketClientStart;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfigration {
/**
 * 系统配置里获取客户端的连接信息属性，然后赋值给WebSocketClientStart对象后注册为bean
 * @ConditionalOnProperty(prefix ="websoket.client",value = "enabled",havingValue = "true")表示websoket.client.enabled属性为true时getWebSocketClient（）才会执行 new WebSocketClientStart()生成对象
 * 否则不执行方法，不生成bean
 * @ConfigurationProperties("websoket.client")表示系统配置文件中websoket.client开头的批量属性赋值给WebSocketClientStart的对象
 * @bean等同于在xml文件中定义bean,然后可以设置属性值，等同于ConfigurationProperties设置，然后最后才会执行@postconstrct标注的非静态方法
 * */
    @Bean
    @ConditionalOnProperty(prefix ="websoket.client",value = "enabled",havingValue = "true")
    @ConfigurationProperties("websoket.client")
    public WebSocketClientStart getWebSocketClient(){
        return new WebSocketClientStart();
    }
}
