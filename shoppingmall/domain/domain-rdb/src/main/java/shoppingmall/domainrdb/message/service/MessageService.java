package shoppingmall.domainrdb.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.ChatErrorCode;
import shoppingmall.common.exception.domain.UserErrorCode;
import shoppingmall.domainrdb.chat.entity.ChatRoom;
import shoppingmall.domainrdb.chat.repository.ChatRoomRepository;

import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.message.dto.ChatMessageRequestDto;
import shoppingmall.domainrdb.message.dto.ChatMessageResponseDto;
import shoppingmall.domainrdb.message.entity.Message;
import shoppingmall.domainrdb.message.repository.MessageRepository;
import shoppingmall.domainrdb.user.entity.User;
import shoppingmall.domainrdb.user.repository.UserRepository;

import java.util.UUID;

@DomainService
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
