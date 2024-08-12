package shppingmall.commerce.user.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shppingmall.commerce.user.entity.User;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // DB 교체
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원을 생성한 후, 아이디와 비밀번호로 회원을 조회할 수 있다. ")
    @Test
    void findByNameAndPassword() {
        //given
        User user = User.builder()
                .name("test")
                .password("1234")
                .build();
        userRepository.save(user);
        //when
        User findUser = userRepository.findByNameAndPassword("test", "1234");

        //then
        assertThat(findUser).isEqualTo(user);

    }


}