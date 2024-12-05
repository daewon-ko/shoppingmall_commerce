package shppingmall.commerce.global.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import shppingmall.commerce.common.handler.WebSocketChatHandler;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketChatHandler webSocketChatHandler;
    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketChatHandler, "/ws/chatRoom/{roomId}")
                .setAllowedOrigins("*");    // 일단 모든 경로에 대하여 허용

    }
}
