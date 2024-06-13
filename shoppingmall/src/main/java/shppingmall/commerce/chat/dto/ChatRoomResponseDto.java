package shppingmall.commerce.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shppingmall.commerce.user.entity.UserRole;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class ChatRoomResponseDto {
    private UUID roomId;
    private Long buyerId;
    private Long sellerId;
    private UserRole userRole;
    private Long senderId;

}
