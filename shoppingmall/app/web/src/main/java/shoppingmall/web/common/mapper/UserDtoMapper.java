package shoppingmall.web.common.mapper;

import shoppingmall.domainrdb.user.UserDomain;

public class UserDtoMapper {
    public static UserDomain toUserDomain(String email) {
        return new UserDomain( email);
    }
}
