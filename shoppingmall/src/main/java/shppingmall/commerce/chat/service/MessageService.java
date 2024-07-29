package shppingmall.commerce.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shppingmall.commerce.chat.dto.ChatMessageDto;
import shppingmall.commerce.chat.entity.ChatRoom;
import shppingmall.commerce.chat.entity.Message;
import shppingmall.commerce.chat.repository.ChatRoomRepository;
import shppingmall.commerce.chat.repository.MessageRepository;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public void saveMessage(ChatMessageDto messageDto, String roomId) {
        String content = messageDto.getContent();
        ChatRoom chatRoom = chatRoomRepository.findById(UUID.fromString(roomId))
                .orElseThrow(() -> new IllegalArgumentException("해당하는 채팅방이 없습니다. 확인해주세요."));


        Long senderId = messageDto.getSenderId();
        User user = userRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 존재하지 않습니다."));

        Message message = Message.builder()
                .chatRoom(chatRoom)
                .content(content)
                .user(user)
                .build();

        messageRepository.save(message);

    }
}
