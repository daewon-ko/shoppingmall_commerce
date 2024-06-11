package shppingmall.commerce.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class ChatRoomResponseDto {
    private UUID id;
    private Long buyerId;
    private Long sellerId;
}
