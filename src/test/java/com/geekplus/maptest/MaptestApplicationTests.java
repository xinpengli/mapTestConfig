package com.geekplus.maptest;

import com.alibaba.fastjson.JSONObject;
import com.geekplus.maptest.Common.JSONUtil;
import com.geekplus.maptest.Componet.API.WebSocket.WebSocketClient;
import com.geekplus.maptest.Componet.API.WebSocket.WebSocketClientStart;
import com.geekplus.maptest.app.Configration.ClientConfigration;
import com.geekplus.maptest.entity.FloorsMapCell;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.security.Signature;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@SpringBootTest(classes = {MaptestApplicationTests.class,WebSocketClientStart.class, WebSocketClient.class, ClientConfigration.class})
public class MaptestApplicationTests extends AbstractTestNGSpringContextTests {

private  static Logger logger= LoggerFactory.getLogger(MaptestApplicationTests.class);
    @Autowired
    WebSocketClientStart webSocketClientStart;
    /*@Autowired
    WebSocketClient webSocketClient;*/
    @Autowired
    ClientConfigration clientConfigration;

@Test
public void master() throws NoSuchMethodException {

    FloorsMapCell a = new FloorsMapCell();
    Assertions.assertThat(2).as("检验是否相等").isEqualTo(2);
//获取类的方法getMethod，第一个参数方法名，第二个参数，方法的参数列表类型数组
    Method method = MaptestApplicationTests.class.getMethod("contextLoads", new Class[]{String.class});
    System.out.println(method.getName());
}

//@Test
    public void webSocketSend(){
    String info="{\"id\":\"GEEK\",\"msgType\":\"UpdateRequestMsg\",\"request\":{\"header\":{\"requestId\":\"5c056953b5-7bc3-a01a-aaae-ca35c878c3a4\",\"clientCode\":\"GEEK\",\"warehouseCode\":\"GEEK\"},\"body\":{\"robotIds\":[],\"shelfCodes\":[],\"floorIds\":[\"1\"],\"cellCodes\":[]}}}";
    JSONObject jsonObject= JSONUtil.jsonToBean(info,JSONObject.class);
    String requestId= (String) jsonObject.getJSONObject("request").getJSONObject("header").get("requestId");

    webSocketClientStart.send(info);
    checkeResponse(WebSocketClient.jsonMap,requestId,10);
    //c056953b5-7bc3-a01a-aaae-ca35c878c3a4

    String repKey=WebSocketClient.jsonMap.entrySet().stream().filter(map->requestId.equals(map.getKey().toString())).map(map->map.getKey()).collect(Collectors.joining());
   Assert.assertEquals(WebSocketClient.jsonMap.get(repKey).getJSONObject("response").getJSONObject("header").getString("code").toString(),"0");


    logger.info("receive response info:{}",WebSocketClient.jsonMap.get("jsonMap"));


}

//    @Test
    public void webSocketSend1(){
        String info = "{\"id\":\"GEEK\",\"msgType\":\"UpdateRequestMsg\",\"request\":{\"header\":{\"requestId\":\"6c056953b5-7bc3-a01a-aaae-ca35c878c3a4\",\"clientCode\":\"GEEK\",\"warehouseCode\":\"GEEK\"},\"body\":{\"robotIds\":[],\"shelfCodes\":[],\"floorIds\":[\"1\"],\"cellCodes\":[]}}}";
        JSONObject jsonObject = JSONUtil.jsonToBean(info, JSONObject.class);
        String requestId = (String) jsonObject.getJSONObject("request").getJSONObject("header").get("requestId");
        webSocketClientStart.send(info);
        checkeResponse(WebSocketClient.jsonMap, requestId, 10);
        logger.info("receive response info:{}", WebSocketClient.jsonMap.get("jsonMap"));
        // String repKey = WebSocketClient.jsonMap.entrySet().stream().filter(RobotConfigHangdler -> requestId.equals(RobotConfigHangdler.getKey().toString())).RobotConfigHangdler(RobotConfigHangdler -> RobotConfigHangdler.getKey()).collect(Collectors.joining());
        Assert.assertEquals(WebSocketClient.jsonMap.get(requestId).getJSONObject("response").getJSONObject("header").getString("code").toString(), "0");
//结束后在aftermethod里移除map中的缓存，最好加上锁，并发时会导致问题

    }
    @Test
    public void test(){
        webSocketSend();
        webSocketSend1();
    }
public  boolean checkeResponse(Map<String,JSONObject> map,String requestId ,int waittime) {
    int time=0;
    while (true){
        time=time+1;
        if (time>waittime){
            logger.error("｛｝超时未相应 超时：｛｝｛｝","requestid:"+requestId,time+"秒");
            return false;

        }
        if(map.get(requestId)==null){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }else {
            Assert.assertEquals(WebSocketClient.jsonMap.get(requestId).getJSONObject("response").getJSONObject("header").getString("code").toString(), "0");
            break;
        }

    }
    return  true;
}

    @Test
    public void getObjectClass(){
        ClientConfigration     client =new ClientConfigration();
        Class<?> classs=client.getClass();

        System.out.println(classs);
        System.out.println(classs.getName());

        for ( Method met : classs.getMethods()){
            System.out.println( met.getName());


        }


    }

}
