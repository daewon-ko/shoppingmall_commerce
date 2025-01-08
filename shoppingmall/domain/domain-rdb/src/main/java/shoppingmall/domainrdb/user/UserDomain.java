package shoppingmall.domainrdb.user;

import lombok.Getter;

@Getter
public class UserDomain {
    private final Long userId;
    private final String name;
    private final String email;


    public UserDomain(final Long userId, final String name, final String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        validateEmail();
        validateName();
    }


    public UserDomain(final String name, final String email) {
        this(null, name, email);
        validateEmail();
        validateName();
    }


    public UserDomain(final String email) {
        this(null, null, email);
        validateEmail();
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
