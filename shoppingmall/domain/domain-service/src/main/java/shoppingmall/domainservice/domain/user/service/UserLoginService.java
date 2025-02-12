//package shoppingmall.domainservice.domain.user.service;
//
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import shoppingmall.common.exception.ApiException;
//import shoppingmall.common.exception.domain.UserErrorCode;
//import shoppingmall.domainrdb.user.UserDomain;
//import shoppingmall.domainrdb.user.service.UserRdbService;
//import shoppingmall.domainredis.domain.auth.AuthSessionStoreService;
//import shoppingmall.domainservice.common.annotation.DomainService;
//import shoppingmall.domainservice.domain.user.dto.request.LoginUserRequestDto;
//import shoppingmall.domainservice.domain.user.dto.request.LoginUserResponseDto;
//
//@DomainService
//@RequiredArgsConstructor
//public class UserLoginService {
//    private final UserRdbService userRdbService;
//    private final UserCredentialService userCredentialService;
//    private final AuthSessionStoreService authSessionStoreService;
//
//    public LoginUserResponseDto login(final LoginUserRequestDto loginUserRequestDto) {
//        UserDomain userDomain = userRdbService.findUserByEmail(loginUserRequestDto.getEmail());
//        boolean isRightPassword = userCredentialService.isPasswordMatch(loginUserRequestDto, userDomain.getEncodedPassword());
//
//        if (!isRightPassword) {
//            throw new ApiException(UserErrorCode.WRONG_PASSWORD);
//        }
//
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        HttpSession session = attributes.getRequest().getSession();
//        String sessionId = session.getId();
//
//        Object savedSession = authSessionStoreService.getSession(sessionId);
//        if (savedSession == null) {
//            authSessionStoreService.saveSession(sessionId, session);
//        }
//
//        return LoginUserResponseDto.builder()
//                .sessionId(sessionId)
//                .email(userDomain.getEmail())
//                .build();
//    }
//}
//
