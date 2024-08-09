package shppingmall.commerce.chat.dto;


import lombok.Builder;
import lombok.Getter;
import shppingmall.commerce.chat.entity.Message;

import java.util.UUID;

@Getter
public class ChatMessageResponseDto {
    private Long id;
    private String content;
    private Long senderId;
    private UUID chatRoomId;

    @Builder
    public ChatMessageResponseDto(Long id, String content, Long senderId, UUID chatRoomId) {
        this.id = id;
        this.content = content;
        this.senderId = senderId;
        this.chatRoomId = chatRoomId;

    }

    public static ChatMessageResponseDto from(Message message) {
        return ChatMessageResponseDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getUser().getId())
                .chatRoomId(message.getChatRoom().getId())
                .build();
    }
}
