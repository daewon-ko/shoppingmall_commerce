package shppingmall.commerce.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shppingmall.commerce.chat.dto.ChatMessageRequestDto;
import shppingmall.commerce.chat.dto.ChatMessageResponseDto;
import shppingmall.commerce.chat.dto.ChatRoomResponseDto;
import shppingmall.commerce.chat.entity.ChatRoom;
import shppingmall.commerce.chat.entity.Message;
import shppingmall.commerce.chat.repository.ChatRoomRepository;
import shppingmall.commerce.chat.repository.MessageRepository;
import shppingmall.commerce.global.exception.ApiException;
import shppingmall.commerce.global.exception.domain.ChatErrorCode;
import shppingmall.commerce.global.exception.domain.UserErrorCode;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatMessageResponseDto saveMessage(ChatMessageRequestDto messageDto, String roomId) {
        String content = messageDto.getContent();
        ChatRoom chatRoom = chatRoomRepository.findById(UUID.fromString(roomId))
                .orElseThrow(() -> new ApiException(ChatErrorCode.NO_EXIST_CHATROOM));


        Long senderId = messageDto.getSenderId();
        User user = userRepository.findById(senderId).orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER));
        Message message = Message.builder()
                .chatRoom(chatRoom)
                .content(content)
                .user(user)
                .build();

        Message savedMessage = messageRepository.save(message);
        return ChatMessageResponseDto.from(savedMessage);

    }

    public Page<ChatMessageResponseDto> getMessageByRoomId(String roomId, Pageable pageable) {
        ChatRoom chatRoom = chatRoomRepository.findById(UUID.fromString(roomId)).orElseThrow(() -> new ApiException(ChatErrorCode.NO_EXIST_CHATROOM));
        return messageRepository.findByChatRoomOrderByCreatedAtDesc(chatRoom, pageable)
                .map(ChatMessageResponseDto::from);
    }
}
