package com.geekplus.maptest.Componet.API.WebSocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value="/websocket")
@Component
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    Timer timer = new Timer("api-callback");
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private volatile static List<Session> sessions = Collections.synchronizedList(new ArrayList());


    File athenafile= new File("/var/log/geekplus/tomcat-rms/athena/athena.log");
   File athenaTollsfile= new File("/var/log/geekplus/test/athenatest/athenatest.log");
   File file;
//    File file= new File("C:\\var\\log\\geekplus\\test\\athenatest\\athenatest.log");

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {

        this.session = session;
        sessions.add(this.session);
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        sessions.remove(this.session);
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        timer.cancel();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);

        //群发消息





        ;
        try {
            sendMessage("服务器消息：连接成功");
            for (WebSocketServer item : webSocketSet) {
                if(this.session.isOpen()){

                    if (message.equals("athena")){
                        file=athenafile;


                    }else if (message.equals("athenatest")){
                        file=athenaTollsfile;
                    }
                    long lastTimeFileSize = 0;


                    // 系统启动10秒后，每200毫秒调用一次callback方法
                    timer.schedule(new TimerTask() {

long lastTimeFileSize=file.length();

                        @Override
                        public void run() {
                            try {
                                long     NowFileSize = file.length();

                                String tmp = null;
                                RandomAccessFile randomFile = new RandomAccessFile(file,"r");
                                if (lastTimeFileSize<=NowFileSize){
//                                    RandomAccessFile randomFile = new RandomAccessFile(file,"r");
                                    randomFile.seek(lastTimeFileSize);

                                    while ((tmp = randomFile.readLine()) != null) {

                                            item.sendMessage(tmp);


//                                        if (item.session.isOpen()){
//                                            item.sendMessage(tmp);
//
//                                        }else {
//                                            timer.cancel();
//                                        }

                                    }
                                    lastTimeFileSize = randomFile.length();
                                }else{
                                    lastTimeFileSize = 0;
                                }

                               /* FileReader fileReader = null;
                                fileReader = new FileReader(file);*/

//                                String readStr=  fileReader.readString();


                                System.out.println("发送信息");


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 0, 200);


                }





            }


        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }


//     * 发生错误时调用
     @OnError
     public void onError(Session session, Throwable error) {
     System.out.println("发生错误");
     error.printStackTrace();
     }




     public void sendMessage(String message) throws IOException {

         synchronized (this.session)
         { this.session.getBasicRemote().sendText(message);};

     //this.session.getAsyncRemote().sendText(message);
     }


     /*
      * 群发自定义消息
      * */
    public static void sendInfo(String message) throws IOException {
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}

