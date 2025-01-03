package shoppingmall.domainrdb.domain.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.domain.chat.entity.ChatRoom;
import shoppingmall.domainrdb.domain.message.repository.MessageRepository;
import shoppingmall.domainrdb.domain.chat.repository.ChatRoomRepository;
import shoppingmall.domainrdb.domain.message.dto.ChatMessageRequestDto;
import shoppingmall.domainrdb.domain.message.dto.ChatMessageResponseDto;
import shoppingmall.domainrdb.domain.message.entity.Message;
import shoppingmall.domainrdb.domain.user.entity.User;
import shoppingmall.domainrdb.domain.user.repository.UserRepository;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.ChatErrorCode;
import shoppingmall.common.exception.domain.UserErrorCode;


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

    public Page<ChatMessageResponseDto> getMessageByRoomId(UUID roomId, Pageable pageable) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new ApiException(ChatErrorCode.NO_EXIST_CHATROOM));
        return messageRepository.findByChatRoomOrderByCreatedAtDesc(chatRoom, pageable)
                .map(ChatMessageResponseDto::from);
    }
}
