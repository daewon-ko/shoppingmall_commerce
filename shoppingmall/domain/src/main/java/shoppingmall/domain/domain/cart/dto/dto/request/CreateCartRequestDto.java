package shoppingmall.domain.domain.cart.dto.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.domain.domain.cart.entity.Cart;
import shoppingmall.domain.domain.user.entity.User;

@Getter
@NoArgsConstructor
public class CreateCartRequestDto {
    @NotNull(message = "사용자 ID를 반드시 입력해주세요.")
    private Long userId;




    @Builder
    private CreateCartRequestDto(Long userId) {
        this.userId = userId;
    }

    public Cart toEntity(User user) {
        return Cart.builder()
                .user(user)
                .build();
    }

    public Cart toEntity() {
        return Cart.builder()
                .build();
    }


}
