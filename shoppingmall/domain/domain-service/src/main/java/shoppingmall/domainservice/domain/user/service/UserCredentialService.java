package shoppingmall.domainservice.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shoppingmall.domainservice.common.annotation.DomainService;
import shoppingmall.domainservice.domain.user.dto.request.LoginUserRequestDto;

@DomainService
@RequiredArgsConstructor
public class UserCredentialService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String encodePassword(final String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean isPasswordMatch(final LoginUserRequestDto loginUserRequestDto, final String password) {
        return bCryptPasswordEncoder.matches(loginUserRequestDto.getPassword(), password);
    }
}
