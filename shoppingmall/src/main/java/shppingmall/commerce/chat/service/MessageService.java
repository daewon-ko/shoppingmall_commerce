package shppingmall.commerce.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shppingmall.commerce.chat.dto.ChatMessageDto;
import shppingmall.commerce.chat.entity.ChatRoom;
import shppingmall.commerce.chat.entity.Message;
import shppingmall.commerce.chat.repository.ChatRoomRepository;
import shppingmall.commerce.chat.repository.MessageRepository;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
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

        /**
         * ChatRoom 객체를 가져왔다.
         * 문제 : Message객체에 저장할 User 객체가 없다.
         * DB Table에는 sender_id 즉 송신지가 필요하다.
         * ChatRoom Entity에는 User(buyer), User(sender) 객체를 소유하고 있지만,
         * 어느 객체가 sender인지는 알 수 없다.
         *
         * sender의 형식이 BUYER인지 SELLER와 같은 '역할'로 정의되어있다.
         * <- why?
         *
         * sender 객체가 무엇인지 어떻게 알 수 있지?
         */

//        UserRole senderType = messageDto.getSenderType();//BUYER 또는 SENDER
        Long senderId = messageDto.getSenderId();
        User user = userRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 존재하지 않습니다."));

        Message message = Message.builder()
                .chatRoom(chatRoom)
                .content(content)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        messageRepository.save(message);


        // ChatMessageDTO -> Message로 변환
//        Message.builder()
//                .


    }
}
