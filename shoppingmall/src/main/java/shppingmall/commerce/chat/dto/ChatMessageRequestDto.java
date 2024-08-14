package shppingmall.commerce.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shppingmall.commerce.chat.entity.MessageType;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ChatMessageRequestDto {
    private MessageType messageType;
    private Long senderId;
    private String content;
}
