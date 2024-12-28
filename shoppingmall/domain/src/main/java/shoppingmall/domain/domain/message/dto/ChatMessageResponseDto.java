package shoppingmall.domain.domain.message.dto;


import lombok.Builder;
import lombok.Getter;
import shoppingmall.domain.domain.message.entity.Message;
import shoppingmall.domain.domain.user.entity.UserRole;

import java.util.UUID;

@Getter
public class ChatMessageResponseDto {
    private Long id;
    private String content;
    private Long senderId;
    private UUID chatRoomId;
    private UserRole senderType;

    @Builder
    protected ChatMessageResponseDto(Long id, String content, Long senderId, UUID chatRoomId, UserRole senderType) {
        this.id = id;
        this.content = content;
        this.senderId = senderId;
        this.chatRoomId = chatRoomId;
        this.senderType = senderType;
    }

    public static ChatMessageResponseDto from(Message message) {
        return ChatMessageResponseDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getUser().getId())
                .senderType(message.getUser().getUserRole())
                .chatRoomId(message.getChatRoom().getId())
                .build();
    }
}
