package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.entity.User;

public class UserEntityMapper {
    public static User toUserEntity(UserDomain userDomain) {
        return User.builder()
                .name(userDomain.getName())
                .email(userDomain.getEmail())
                .build();
    }

    public static UserDomain toUserDomain(User user) {
        return UserDomain.createForRead(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getUserRole());
    }
}
