package shoppingmall.web.common.validation.user;

import org.springframework.stereotype.Component;
import shoppingmall.domainservice.domain.user.dto.request.LoginUserRequestDto;

@Component
public class UserLoginValidator {
    public void validate(final LoginUserRequestDto loginUserRequestDto) {
        if (loginUserRequestDto.getEmail() == null || loginUserRequestDto.getEmail().isEmpty()) {
            throw new IllegalStateException("User email is null or empty");
        }
        if (loginUserRequestDto.getPassword() == null || loginUserRequestDto.getPassword().isEmpty()) {
            throw new IllegalStateException("User password is null or empty");
        }
    }
}
