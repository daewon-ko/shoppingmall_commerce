package shoppingmall.domainrdb.user;

import lombok.Getter;
import shoppingmall.domainrdb.user.entity.UserRole;

@Getter
public class UserDomain {
    private final UserId userId;
    private final String name;
    private final String email;
    private final String encodedPassword;
    private final UserRole userRole;


    private UserDomain(UserId userId, String name, String email, String encodedPassword, UserRole userRole) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.userRole = userRole;
    }

    public static UserDomain createForWrite(final String name, final String email, final String encodedPassword, final UserRole userRole) {
        return new UserDomain(null, name, email, encodedPassword, userRole);
    }

    public static UserDomain createForRead(final Long userId, final String name, final String email, final String encodedPassword, final UserRole userRole) {
        return new UserDomain(new UserId(userId), name, email, encodedPassword, userRole);
    }


    public void validateName() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("User name is empty");
        }

    }

    public void validateEmail() {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("User email is empty");
        }
    }
}
