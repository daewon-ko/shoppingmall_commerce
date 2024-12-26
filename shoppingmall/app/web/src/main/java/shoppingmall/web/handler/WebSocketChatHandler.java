package shoppingmall.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import shoppingmall.domain.domain.message.dto.ChatMessageRequestDto;
import shoppingmall.domain.domain.message.entity.MessageType;
import shoppingmall.domain.domain.message.service.MessageService;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageService messageService;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        String roomId = getRoomId(session.getUri());
        sessions.put(session.getId(), session);
        session.getAttributes().put("roomId", roomId);
        // TODO : 현재는 인증에 대한 부분은 일단 생략. Principal 등의 객체는 인증 부분 학습 후 적용필요
        log.info("웹소켓 연결 : session id = {}", session.getId() + "채팅방 번호 : {} ", roomId);
    }

    @Override
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message) throws Exception {


        String roomId = (String) session.getAttributes().get("roomId");
        String payload = (String) message.getPayload();

        ChatMessageRequestDto chatMessage = objectMapper.readValue(payload, ChatMessageRequestDto.class);
        if (chatMessage.getMessageType().equals(MessageType.TALK)) {
            messageService.saveMessage(chatMessage, roomId);

        }
        sendMessageToRoom(roomId, chatMessage);

    }

    private void sendMessageToRoom(String roomId, ChatMessageRequestDto message) {
        TextMessage textMessage;
        try {
            textMessage = new TextMessage(objectMapper.writeValueAsString(message));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // TODO : 로직 개선 필요.
        // session이 열려있으면 메세지를 또 보낸다면, 메시지 중복전송할 수 있음. 그럼에도 session이 열려있음을 확인해야한다?
        // 또한 session 자체를 얼마나 유지할지또한 고민해봐야한다.
        // TODO:  한 명의 유저가 로그아웃인 상태라도 메세지는 전송되어야하는 문제 개선 필요


        sessions.values().stream()
                .filter(session -> session.isOpen() && roomId.equals(session.getAttributes().get("roomId")))
                .distinct() // 중복된 세션을 걸러냅니다.
                .forEach(session -> {
                    try {
                        session.sendMessage(textMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("WebSocket connection closed: " + session.getId());
    }

    private String getRoomId(URI uri) {
        String path = uri.getPath();
        String[] segments = path.split("/");
        return segments[segments.length - 1];
    }
}
