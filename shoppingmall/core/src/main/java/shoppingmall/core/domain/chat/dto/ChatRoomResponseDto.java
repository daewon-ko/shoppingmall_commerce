package shoppingmall.core.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.core.domain.user.entity.UserRole;

import java.util.UUID;

@Getter
public class  ChatRoomResponseDto {
    private UUID roomId;
    private Long buyerId;
    private Long sellerId;
    private Long senderId;
    private UserRole userRole;

    @Builder
    private ChatRoomResponseDto(UUID roomId, Long buyerId, Long sellerId, Long senderId, UserRole userRole) {
        this.roomId = roomId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.senderId = senderId;
        this.userRole = userRole;
    }
}
