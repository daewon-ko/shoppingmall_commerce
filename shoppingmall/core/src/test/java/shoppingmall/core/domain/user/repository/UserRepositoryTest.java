package shoppingmall.core.domain.user.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shoppingmall.core.domain.user.entity.User;
import shoppingmall.core.domain.user.entity.UserRole;
import shoppingmall.core.domain.user.repository.UserRepository;
import shoppingmall.core.support.RepositoryTestSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static shoppingmall.core.support.TestFixture.createUser;


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

    @DisplayName("email을 기반으로 User를 조회할 수 있다.")
    @Test
    void findUserByEmail() {

        //given
        User user = createUser("test@email.com", "test", "test", UserRole.BUYER);

        userRepository.save(user);
        //when
        User findUser = userRepository.findByEmail("test@email.com").orElseThrow(() -> new IllegalStateException("해당하는 회원이 없습니다."));

        //then
        assertThat(findUser).isEqualTo(user);

    }





}