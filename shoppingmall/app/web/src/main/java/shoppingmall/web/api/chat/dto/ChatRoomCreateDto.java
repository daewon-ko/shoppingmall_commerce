package shoppingmall.web.api.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
//@JsonDeserialize(builder = ChatRoomCreateDto.ChatRoomCreateDtoBuilder.class)
public class ChatRoomCreateDto {
    @NotNull(message = "상품번호를 반드시 입력해주세요.")
    private Long productId;
    @NotNull(message = "구매자 아이디를 반드시 입력해주세요.")
    private Long buyerId;
    @NotNull(message = "판매자 아이디를 반드시 입력해주세요.")
    private Long sellerId;

    @Builder
    public ChatRoomCreateDto(Long productId, Long buyerId, Long sellerId) {
        this.productId = productId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
    }



}
