package shppingmall.commerce.common.handler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import shppingmall.commerce.chat.dto.ChatMessageDto;
import shppingmall.commerce.chat.entity.MessageType;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        String roomId = getRoomId(session.getUri());
        sessions.put(session.getId(), session);
        session.getAttributes().put("roomId", roomId);
        // TODO : 현재는 인증에 대한 부분은 일단 생략.
        // 일단은 sender 등에 대한 정보를 임의로 넣어보기로 한다.
//        session.getAttributes().put("username", session.getPrincipal().getName());


        ChatMessageDto enterMessage = ChatMessageDto.builder()
                .messageType(MessageType.ENTER)
                .sender("A")
                .content("A" + " has entered the room.")
                .build();

        sendMessageToRoom(roomId, enterMessage);
        System.out.println("New WebSocket connection: " + session.getId() + " in room " + roomId);
    }

    @Override
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message) throws Exception {


        String roomId = (String) session.getAttributes().get("roomId");
        String payload = (String)message.getPayload();
        ChatMessageDto chatMessage = objectMapper.readValue(payload, ChatMessageDto.class);
        if (chatMessage.getMessageType().equals(MessageType.TALK)) {
            sendMessageToRoom(roomId, chatMessage);
        }



    }

    private void sendMessageToRoom(String roomId, ChatMessageDto message) {
        TextMessage textMessage;
        try {
            textMessage = new TextMessage(objectMapper.writeValueAsString(message));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // TODO : 로직 개선 필요.
        // session이 열려있으면 메세지를 또 보낸다면, 메시지 중복전송할 수 있음. 그럼에도 session이 열려있음을 확인해야한다?
        // 또한 session 자체를 얼마나 유지할지또한 고민해봐야한다.
        // 메시지를 보낼때마다 session이 열리고 닫힌다면 서버에 문제가 생기지 않을까?
//        for (WebSocketSession session : sessions.values()) {
//            if (session.isOpen() && roomId.equals(session.getAttributes().get("roomId"))) {
//                try {
//                    session.sendMessage(textMessage);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
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
