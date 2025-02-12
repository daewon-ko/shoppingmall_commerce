package shoppingmall.web.api.user.usecase;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.user.entity.UserRole;
import shoppingmall.domainservice.domain.user.dto.request.LoginUserRequestDto;
import shoppingmall.domainservice.domain.user.dto.request.LoginUserResponseDto;
import shoppingmall.domainservice.domain.user.service.UserCreateService;
import shoppingmall.domainservice.domain.user.dto.request.CreateUserRequestDto;
import shoppingmall.web.common.annotataion.Usecase;
import shoppingmall.web.common.validation.user.UserCreateValidator;
import shoppingmall.web.common.validation.user.UserLoginValidator;

@Usecase
@RequiredArgsConstructor
public class UserUsecase {
    private final UserCreateService userCreateService;
//    private final UserLoginService userLoginService;
    private final UserCreateValidator userCreateValidator;
    private final UserLoginValidator userLoginValidator;

    public Long registerUser(final CreateUserRequestDto createUserRequestDto, final UserRole userRole) {
        userCreateValidator.validate(createUserRequestDto);
        return userCreateService.registerUser(createUserRequestDto, userRole);
    }

//    public LoginUserResponseDto login(final LoginUserRequestDto loginUserRequestDto) {
//        userLoginValidator.validate(loginUserRequestDto);
//        return userLoginService.login(loginUserRequestDto);
//    }


}
