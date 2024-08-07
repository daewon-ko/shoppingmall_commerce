package shppingmall.commerce.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter

public class ChatRoomCreateDto {
    @NotBlank(message = "상품번호를 반드시 입력해주세요.")
    private Long productId;
    @NotBlank(message = "구매자 아이디를 반드시 입력해주세요.")
    private Long buyerId;
    @NotBlank(message = "판매자 아이디를 반드시 입력해주세요.")
    private Long sellerId;

    @Builder
    private ChatRoomCreateDto(Long productId, Long buyerId, Long sellerId) {
        this.productId = productId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
    }
}
