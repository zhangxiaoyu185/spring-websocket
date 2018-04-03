package com.xiaoyu.lingdian.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.xiaoyu.lingdian.entity.WebSocketClient;

public class MyWebSocketHandler extends TextWebSocketHandler {

    // 保存所有的用户session
    private static Map<String, WebSocketClient> socketClients = new HashMap<String, WebSocketClient>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        //接收到客户端消息时调用
        //获取发送消息的ip
        WebSocketClient sendClient = socketClients.get(session.getId());
        String clientIp = sendClient.getClientIp();

        //向所有客户端群发收到的消息
        Set<String> sessionIDs = socketClients.keySet();
        Iterator<String> i = sessionIDs.iterator();
        while (i.hasNext()) {
            String sessionID = i.next();
            WebSocketClient client = socketClients.get(sessionID);
            WebSocketSession clientSession = client.getSocketSession();
            try {
                clientSession.sendMessage(new TextMessage((clientIp + "：" + message.getPayload()).getBytes("UTF-8")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        // 与客户端完成连接后调用
        //获取连接的唯一session id
        String sessionID = session.getId();

        //获取客户端ip地址
        String clientIp = session.getRemoteAddress().getAddress().getHostAddress();
        //将已连接的socket客户端保存
        WebSocketClient client = new WebSocketClient();
        client.setClientIp(clientIp);
        client.setSocketSession(session);

        //保存已连接的客户端信息
        socketClients.put(sessionID, client);
    }

    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {
        // 消息传输出错时调用
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {
        // 一个客户端连接断开时关闭
        //从保存的客户端集合中删除关闭的客户端
        String sessionID = session.getId();
        socketClients.remove(sessionID);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
