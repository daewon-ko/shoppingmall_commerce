package shppingmall.commerce.user.dto;


import lombok.Builder;
import lombok.Getter;

/**
 * TODO : 보안상 이유라 아래와 같이 작성하면 안되지만, 일단은 큰 틀에서 이해를 위해 최대한 간단하게 User쪽 작성
 */
@Getter
public class LoginUserRequestDto {
    private String name;
    private String password;

    @Builder
    private LoginUserRequestDto(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
