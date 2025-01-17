package shoppingmall.domainservice.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.UserErrorCode;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.service.UserRdbService;
import shoppingmall.domainservice.common.annotation.DomainService;
import shoppingmall.domainservice.domain.user.dto.request.LoginUserRequestDto;

@DomainService
@RequiredArgsConstructor
public class UserLoginService {
    private final UserRdbService userRdbService;
    private final UserCredentialService userCredentialService;

    public void login(final LoginUserRequestDto loginUserRequestDto) {


        UserDomain userDomain = userRdbService.findUserByEmail(loginUserRequestDto.getEmail());
        boolean isRightPassword = userCredentialService.isPasswordMatch(loginUserRequestDto, userDomain.getEncodedPassword());
        if (isRightPassword) {
            //로그인성공
            // Redis Session Storage에 저장

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            jakarta.servlet.http.HttpSession session = attributes.getRequest().getSession();

        }else {
            throw new ApiException(UserErrorCode.WRONG_PASSWORD);
        }


    }

}
