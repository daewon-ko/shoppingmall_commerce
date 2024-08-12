package shppingmall.commerce.user.dto;

import lombok.Getter;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;

@Getter
public class CreateUserRequestDto {
    private String name;
    private String password;

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
