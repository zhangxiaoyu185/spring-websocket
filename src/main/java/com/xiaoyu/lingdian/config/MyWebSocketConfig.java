package com.xiaoyu.lingdian.config;

import com.xiaoyu.lingdian.Interceptor.HandShakeInterceptor;
import com.xiaoyu.lingdian.handler.MyWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class MyWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //前台 可以使用websocket环境
        registry.addHandler(myWebSocketHandler(),"/ws").addInterceptors(new HandShakeInterceptor())
                .setAllowedOrigins("http://192.168.102.83:8096", "http://localhost:8096", "http://127.0.0.1:8096");

        //前台 不可以使用websocket环境，则使用sockjs进行模拟连接
        registry.addHandler(myWebSocketHandler(), "/ws/sockjs").addInterceptors(new HandShakeInterceptor())
                .setAllowedOrigins("http://192.168.102.83:8096", "http://localhost:8096", "http://127.0.0.1:8096").withSockJS();
    }

    // websocket 处理类
    @Bean
    public WebSocketHandler myWebSocketHandler(){
        return new MyWebSocketHandler();
    }
}
