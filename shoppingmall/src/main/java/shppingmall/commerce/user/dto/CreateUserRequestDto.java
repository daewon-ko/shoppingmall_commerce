package shppingmall.commerce.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;

@Getter
public class CreateUserRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String password;

    @Builder
    private CreateUserRequestDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User toBuyerEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .userRole(UserRole.BUYER)
                .build();

    }

    public User toSellerEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .userRole(UserRole.SELLER)
                .build();

    }

}
