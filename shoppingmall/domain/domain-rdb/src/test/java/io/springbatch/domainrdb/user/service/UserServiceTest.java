package io.springbatch.domainrdb.user.service;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.UserErrorCode;
import shoppingmall.domainrdb.domain.user.dto.CreateUserRequestDto;
import shoppingmall.domainrdb.domain.user.entity.User;
import shoppingmall.domainrdb.domain.user.entity.UserRole;
import shoppingmall.domainrdb.domain.user.repository.UserRepository;
import shoppingmall.domainrdb.support.IntegrationTestSupport;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static shoppingmall.domainrdb.support.TestFixture.createUser;

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
                            .isInstanceOf(ApiException.class);

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
                .orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER))
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
                .orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER))
                .getUserRole())
                .isEqualTo(UserRole.BUYER);
    }




    private static CreateUserRequestDto getCreateUserRequestDto() {
        CreateUserRequestDto request = CreateUserRequestDto.builder()
                .name("test")
                .password("1234")
                .build();
        return request;
    }





}