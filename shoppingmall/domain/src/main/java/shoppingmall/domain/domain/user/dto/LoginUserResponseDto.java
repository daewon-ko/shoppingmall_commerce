package shoppingmall.domain.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domain.domain.user.entity.User;

@Getter
public class LoginUserResponseDto {
    private Long id;
    private String username;

    @Builder
    private LoginUserResponseDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static LoginUserResponseDto from(User user) {
        return LoginUserResponseDto.builder()
                .id(user.getId())
                .username(user.getName())
                .build();
    }
}
