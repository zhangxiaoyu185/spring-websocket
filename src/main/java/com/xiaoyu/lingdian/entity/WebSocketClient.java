package com.xiaoyu.lingdian.entity;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketClient {
	private WebSocketSession socketSession;
	private String clientIp;
	
	public WebSocketSession getSocketSession() {
		return socketSession;
	}
	
	public void setSocketSession(WebSocketSession socketSession) {
		this.socketSession = socketSession;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
}
