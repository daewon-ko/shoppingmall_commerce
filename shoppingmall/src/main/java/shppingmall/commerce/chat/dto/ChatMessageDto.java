package shppingmall.commerce.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import shppingmall.commerce.chat.entity.MessageType;

@Builder
@AllArgsConstructor
public class ChatMessageDto {
    private MessageType messageType;
    private String sender;
    private Long roomId;
    private String content;
}
