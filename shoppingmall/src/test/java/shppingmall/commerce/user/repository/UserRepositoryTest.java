package shppingmall.commerce.user.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shppingmall.commerce.support.RepositoryTestSupport;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest extends RepositoryTestSupport {
    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원을 생성한 후, 아이디와 비밀번호로 회원을 조회할 수 있다. ")
    @Test
    void findByNameAndPassword() {
        //given
        User user = createUser("test", "1234", UserRole.BUYER);
        userRepository.save(user);
        //when
        User findUser = userRepository.findByNameAndPassword("test", "1234");

        //then
        assertThat(findUser).isEqualTo(user);

    }



    private static User createUser(String name, String password, UserRole userRole) {
        User user = User.builder()
                .name(name)
                .password(password)
                .userRole(userRole)
                .build();
        return user;
    }


}