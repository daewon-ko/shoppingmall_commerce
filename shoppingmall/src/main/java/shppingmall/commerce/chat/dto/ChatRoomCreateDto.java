package shppingmall.commerce.chat.dto;

import lombok.Getter;

@Getter
public class ChatRoomCreateDto {
    private Long productId;
    private Long buyerId;
    private Long sellerId;
}
