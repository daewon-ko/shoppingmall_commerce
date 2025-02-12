package shoppingmall.web.api.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shoppingmall.common.ApiResponse;
import shoppingmall.domainservice.domain.user.dto.request.LoginUserRequestDto;
import shoppingmall.domainservice.domain.user.dto.request.LoginUserResponseDto;
import shoppingmall.web.api.user.usecase.UserUsecase;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserUsecase userUsecase;

//    @PostMapping("/login")
//    public ApiResponse<LoginUserResponseDto> login(final LoginUserRequestDto loginUserRequestDto) {
//        LoginUserResponseDto loginUserResponseDto = userUsecase.login(loginUserRequestDto);
//        return ApiResponse.of(HttpStatus.OK, loginUserResponseDto);
//    }

}
