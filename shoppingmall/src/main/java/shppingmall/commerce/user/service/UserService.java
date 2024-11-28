package shppingmall.commerce.user.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shppingmall.commerce.global.exception.ApiException;
import shppingmall.commerce.global.exception.domain.UserErrorCode;
import shppingmall.commerce.user.dto.CreateUserRequestDto;
import shppingmall.commerce.user.dto.LoginUserRequestDto;
import shppingmall.commerce.user.dto.LoginUserResponseDto;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public Long registerBuyer(CreateUserRequestDto createUserRequest) {

        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            new ApiException(UserErrorCode.ALREADY_REGISTERED_EMAIL);
        }

        User user = User.builder()
                .email(createUserRequest.getEmail())
                .name(createUserRequest.getName())
                .password(bCryptPasswordEncoder.encode(createUserRequest.getPassword()))
                .userRole(UserRole.BUYER)
                .build();

        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    // TODO : Seller, Buyer 분기처리 좀더 섬세히 작성 필요

    @Transactional
    public Long registerSeller(CreateUserRequestDto createUserRequest) {

        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            new ApiException(UserErrorCode.ALREADY_REGISTERED_EMAIL);
        }

        User user = User.builder()
                .email(createUserRequest.getEmail())
                .name(createUserRequest.getName())
                .password(bCryptPasswordEncoder.encode(createUserRequest.getPassword()))
                .userRole(UserRole.SELLER)
                .build();
        user = userRepository.save(user);
        return user.getId();
    }


    /**
     * TODO : HttpSession 같은 웹계층에서 사용되는 것을 Service Layer에 끌고 오는 것이 적절할까?
     * ResponseDto로 감싸서 Presentation Layer에 반환하고 싶었고, 동시에 Session을 해당 계층에서만 사용하는 경우엔
     * Session 객체에 User 정보를 올바르게 담지 못한다고 생각해서 조금 이상하지만 Service Layer까지 끌고 들어옴.
     */
//    public LoginUserResponseDto login(LoginUserRequestDto loginUserRequestDto, HttpSession httpSession) {
//        User findUser = userRepository.findByNameAndPassword(loginUserRequestDto.getName(), loginUserRequestDto.getPassword());
//        httpSession.setAttribute("user", findUser);
//        return LoginUserResponseDto.from(findUser);
//    }

    public User findUserByIdAndSeller(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER));
        if (!findUser.getUserRole().equals(UserRole.SELLER)) {
            throw new ApiException(UserErrorCode.USER_NOT_SELLER);
        }
        return findUser;
    }
}
