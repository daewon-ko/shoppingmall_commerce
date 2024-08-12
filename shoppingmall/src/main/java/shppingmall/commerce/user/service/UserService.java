package shppingmall.commerce.user.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shppingmall.commerce.user.dto.CreateUserRequestDto;
import shppingmall.commerce.user.dto.LoginUserRequestDto;
import shppingmall.commerce.user.dto.LoginUserResponseDto;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;


    @Transactional
    public void register(CreateUserRequestDto createUserRequest) {
        User user = createUserRequest.toEntity();
        userRepository.save(user);
    }

    public LoginUserResponseDto login(LoginUserRequestDto loginUserRequestDto, HttpSession httpSession) {
        User findUser = userRepository.findByNameAndPassword(loginUserRequestDto.getName(), loginUserRequestDto.getPassword());
        httpSession.setAttribute("user", findUser);
        return LoginUserResponseDto.from(findUser);
    }
}
