package shppingmall.commerce.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shppingmall.commerce.message.entity.MessageType;
import shppingmall.commerce.user.entity.UserRole;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ChatMessageRequestDto {
    private MessageType messageType;
    private UserRole senderType;
    private Long senderId;
    private String content;
}
