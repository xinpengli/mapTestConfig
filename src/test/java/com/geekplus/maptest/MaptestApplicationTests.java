package com.geekplus.maptest;

import com.geekplus.maptest.Componet.API.WebSocket.WebSocketClient;
import com.geekplus.maptest.Componet.API.WebSocket.WebSocketClientStart;
import com.geekplus.maptest.entity.FloorsMapCell;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Map;


@SpringBootTest(classes = {MaptestApplicationTests.class,WebSocketClientStart.class, WebSocketClient.class})
public class MaptestApplicationTests extends AbstractTestNGSpringContextTests {

private  static Logger logger= LoggerFactory.getLogger(MaptestApplicationTests.class);
    @Autowired
    WebSocketClientStart webSocketClientStart;
    /*@Autowired
    WebSocketClient webSocketClient;*/

@Test
public void master() throws NoSuchMethodException {

    FloorsMapCell a = new FloorsMapCell();
    Assertions.assertThat(2).as("检验是否相等").isEqualTo(2);
//获取类的方法getMethod，第一个参数方法名，第二个参数，方法的参数列表类型数组
    Method method = MaptestApplicationTests.class.getMethod("contextLoads", new Class[]{String.class});
    System.out.println(method.getName());
}

@Test
    public void webSocketSend(){
    String info="{\"id\":\"GEEK\",\"msgType\":\"UpdateRequestMsg\",\"request\":{\"header\":{\"requestId\":\"c056953b5-7bc3-a01a-aaae-ca35c878c3a4\",\"clientCode\":\"GEEK\",\"warehouseCode\":\"GEEK\"},\"body\":{\"robotIds\":[],\"shelfCodes\":[],\"floorIds\":[\"1\"],\"cellCodes\":[]}}}";
    webSocketClientStart.send(info);

    checkeResponse(WebSocketClient.jsonMap,10);

    logger.info("receive response info:{}",WebSocketClient.jsonMap.get("jsonMap"));


}
public  boolean checkeResponse(Map<String,String> map,int waittime) {
    int time=0;
    while (true){
        time=time+1;
        if (time>waittime){
            return false;
        }
        if(map.get("jsonMap")==null){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }else {
            break;
        }

    }
    return  true;
}

}
