package shppingmall.commerce.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shppingmall.commerce.chat.entity.MessageType;
import shppingmall.commerce.user.entity.UserRole;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ChatMessageDto {
    private MessageType messageType;
    private Long senderId;
//    private UserRole senderType;
    private String content;
}
