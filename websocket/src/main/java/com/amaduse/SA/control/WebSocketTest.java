package com.amaduse.SA.control;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/hello")
public class WebSocketTest  {
    public WebSocketTest(){
        System.out.println("WebsocketTest..");
    }

    @OnOpen
    public void onopen(Session session){
        System.out.println("连接成功");
        try {
            session.getBasicRemote().sendText("hello client...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @OnClose
    public void onclose(Session session){
        System.out.println("close....");

    }
    @OnMessage
    public void onsend(Session session,String msg){
        try {
            session.getBasicRemote().sendText("client"+session.getId()+"say:"+msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}