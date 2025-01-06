package shoppingmall.domainrdb.user;

import lombok.Getter;

@Getter
public class UserDomain {
    private String name;
    private String email;



    public UserDomain(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
