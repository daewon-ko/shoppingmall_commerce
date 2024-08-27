package shppingmall.commerce.user.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    @Transactional
    public Long registerBuyer(CreateUserRequestDto createUserRequest) {
        User user = createUserRequest.toBuyerEntity();
        user = userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public Long registerSeller(CreateUserRequestDto createUserRequest) {
        User user = createUserRequest.toSellerEntity();
        user = userRepository.save(user);
        return user.getId();
    }

    /**
     * TODO : HttpSession 같은 웹계층에서 사용되는 것을 Service Layer에 끌고 오는 것이 적절할까?
     * ResponseDto로 감싸서 Presentation Layer에 반환하고 싶었고, 동시에 Session을 해당 계층에서만 사용하는 경우엔
     * Session 객체에 User 정보를 올바르게 담지 못한다고 생각해서 조금 이상하지만 Service Layer까지 끌고 들어옴.
     */
    public LoginUserResponseDto login(LoginUserRequestDto loginUserRequestDto, HttpSession httpSession) {
        User findUser = userRepository.findByNameAndPassword(loginUserRequestDto.getName(), loginUserRequestDto.getPassword());
        httpSession.setAttribute("user", findUser);
        return LoginUserResponseDto.from(findUser);
    }

    public User findUserByIdAndSeller(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다."));
        if (!findUser.getUserRole().equals(UserRole.SELLER)) {
            throw new IllegalStateException("해당 회원은 판매자가 아닙니다. 판매자만이 상품을 생성할수 있습니다.");
        }
        return findUser;
    }
}
