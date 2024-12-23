package shoppingmall.core.domain.user.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * TODO : 보안상 이유라 아래와 같이 작성하면 안되지만, 일단은 큰 틀에서 이해를 위해 최대한 간단하게 User쪽 작성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginUserRequestDto {
    private String email;
    private String password;

    @Builder
    private LoginUserRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
