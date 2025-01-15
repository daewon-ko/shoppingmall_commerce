package shoppingmall.domainrdb.user;

import lombok.Getter;

@Getter
public class UserDomain {
    private final UserId userId;
    private final String name;
    private final String email;


    private UserDomain(UserId userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        validateEmail();
        validateEmail();
    }

    public static UserDomain createForWrite(String name, String email) {
        return new UserDomain(null, name, email);
    }

    public static UserDomain createForRead(Long id, String name, String email) {
        return new UserDomain(UserId.from(id), name, email);
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
