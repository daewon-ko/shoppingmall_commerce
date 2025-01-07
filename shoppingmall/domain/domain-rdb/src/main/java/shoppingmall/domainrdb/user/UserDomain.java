package shoppingmall.domainrdb.user;

import lombok.Getter;

@Getter
public class UserDomain {
    private String name;
    private String email;



    public UserDomain(String name, String email) {
        this.name = name;
        this.email = email;
        validateEmail();
        validateName();
    }


    public UserDomain(String email) {
        this.email = email;
        validateEmail();
    }

    public void validateName() {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("User name is empty");
        }

    }

    public void validateEmail() {
        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("User email is empty");
        }
    }
}
