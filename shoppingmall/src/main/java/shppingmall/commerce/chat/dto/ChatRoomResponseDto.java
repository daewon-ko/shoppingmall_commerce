package shppingmall.commerce.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shppingmall.commerce.user.entity.UserRole;

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
