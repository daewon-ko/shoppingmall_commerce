package shoppingmall.web.api.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.domainrdb.message.entity.MessageType;
import shoppingmall.domainrdb.user.entity.UserRole;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class
ChatMessageRequestDto {
    private MessageType messageType;
    private UserRole senderType;
    private Long senderId;
    private String content;
}
