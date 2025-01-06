package shoppingmall.web.common.mapper;

import shoppingmall.domainrdb.user.UserDomain;

public class UserMapper {
    public static UserDomain toUserDomain(String email) {
        return new UserDomain(null, email);
    }
}
