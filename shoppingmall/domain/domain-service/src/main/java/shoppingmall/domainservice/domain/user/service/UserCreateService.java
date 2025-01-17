package shoppingmall.domainservice.domain.user.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.UserErrorCode;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.entity.UserRole;
import shoppingmall.domainrdb.user.service.UserRdbService;
import shoppingmall.domainservice.common.annotation.DomainService;
import shoppingmall.domainservice.domain.user.dto.request.CreateUserRequestDto;

@DomainService
@RequiredArgsConstructor
public class UserCreateService {
    private final UserRdbService userRdbService;
    private final UserCredentialService userCredentialService;


    public Long registerUser(final CreateUserRequestDto createUserRequestDto, final UserRole userRole) {

        // Email 중복여부 검사
        Boolean registeredEmail = userRdbService.isRegisteredEmail(createUserRequestDto.getEmail());
        if (registeredEmail) {
            // TODO : Exception 처리
            throw new ApiException(UserErrorCode.ALREADY_REGISTERED_EMAIL);
        }


        final String encodedPassword = userCredentialService.encodePassword(createUserRequestDto.getPassword());

        final UserDomain userDomain = createUserRequestDto.toUserDomain(userRole, encodedPassword);

        return userRdbService.registerUser(userDomain);

    }
}
