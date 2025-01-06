package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.entity.User;

public class UserEntityMapper {
    public static User toEntity(UserDomain userDomain) {
        return User.builder()
                .name(userDomain.getName())
                .email(userDomain.getEmail())
                .build();
    }
}
