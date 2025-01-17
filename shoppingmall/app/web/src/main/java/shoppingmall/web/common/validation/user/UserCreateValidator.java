package shoppingmall.web.common.validation.user;

import org.springframework.stereotype.Component;
import shoppingmall.domainservice.domain.user.dto.request.CreateUserRequestDto;

@Component
public class UserCreateValidator {
    public void validate(final CreateUserRequestDto createUserRequestDto) {
        if (createUserRequestDto.getName() == null || createUserRequestDto.getName().isEmpty()) {
            throw new IllegalArgumentException("name is null");
        }
        if (createUserRequestDto.getEmail() == null || createUserRequestDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("email is null");
        }
        if (createUserRequestDto.getPassword() == null || createUserRequestDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("password is invalid");
        }

    }
}
