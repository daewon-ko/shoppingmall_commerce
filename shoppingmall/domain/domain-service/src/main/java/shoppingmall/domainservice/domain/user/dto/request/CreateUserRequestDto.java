package shoppingmall.domainservice.domain.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.entity.UserRole;

@Getter
public class CreateUserRequestDto {
    private String email;
    private String name;
    private String password;

    @Builder
    private CreateUserRequestDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    // UserDomain으로 변환하는 메소드
    public UserDomain toUserDomain(final UserRole userRole, final String encodedPassword) {
        return UserDomain.createForWrite(name, email, encodedPassword, userRole);
    }


}
