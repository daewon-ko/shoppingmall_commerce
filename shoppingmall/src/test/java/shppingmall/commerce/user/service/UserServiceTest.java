package shppingmall.commerce.user.service;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import shppingmall.commerce.support.IntegrationTestSupport;
import shppingmall.commerce.user.dto.CreateUserRequestDto;
import shppingmall.commerce.user.dto.LoginUserRequestDto;
import shppingmall.commerce.user.dto.LoginUserResponseDto;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static shppingmall.commerce.support.TestFixture.createUser;

class UserServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Mock
    private HttpSession httpSession;

    @DisplayName("회원가입 후 Seller 조회 시나리오")
    @TestFactory
    Collection<DynamicTest> findUserByUserRole() {
        User seller = createUser("test", "1234", UserRole.SELLER);
        User buyer = createUser("test", "1234", UserRole.BUYER);
        userRepository.save(seller);
        userRepository.save(buyer);

        return List.of(
                DynamicTest.dynamicTest("판매자를 조회할 수 있다.", () -> {
                    //given
                    Long sellerId = seller.getId();

                    //when
                    User findUser = userService.findUserByIdAndSeller(sellerId);
                    //then
                    assertThat(findUser.getUserRole()).isEqualTo(UserRole.SELLER);

                }),
                DynamicTest.dynamicTest("구매자를 조회시 예외가 발생한다.", () -> {
                    //given
                    Long buyerId = buyer.getId();

                    //when, then
                    assertThatThrownBy(() -> userService.findUserByIdAndSeller(buyerId)).hasMessage("해당 회원은 판매자가 아닙니다. 판매자만이 상품을 생성할수 있습니다.")
                            .isInstanceOf(IllegalStateException.class);

                })
        );

    }


    @DisplayName("구매자 회원을 생성한다.")
    @Test
    void registerSeller() {
        //given
        CreateUserRequestDto request = getCreateUserRequestDto();

        //when
        Long savedId = userService.registerSeller(request);

        //then
        assertThat(userRepository.findById(savedId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다."))
                .getUserRole())
                .isEqualTo(UserRole.SELLER);
    }


    @DisplayName("판매자 회원을 생성한다.")
    @Test
    void registerBuyer() {
        //given
        CreateUserRequestDto request = getCreateUserRequestDto();

        //when
        Long savedId = userService.registerBuyer(request);

        //then
        assertThat(userRepository.findById(savedId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다."))
                .getUserRole())
                .isEqualTo(UserRole.BUYER);
    }

    @DisplayName("회원을 생성한후, 가입시 입력한 id, password로 로그인할 수 있다.")
    @Test
    void loginUser() {
        //given
        User user = createUser("test", "1234", UserRole.SELLER);
        userRepository.save(user);
        LoginUserRequestDto loginUserRequestDto = LoginUserRequestDto.builder()
                .name("test")
                .password("1234")
                .build();


        //when
        LoginUserResponseDto responseDto = userService.login(loginUserRequestDto, httpSession);

        //then
        assertThat(responseDto.getUsername()).isEqualTo("test");

    }


    private static CreateUserRequestDto getCreateUserRequestDto() {
        CreateUserRequestDto request = CreateUserRequestDto.builder()
                .name("test")
                .password("1234")
                .build();
        return request;
    }





}