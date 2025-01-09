package shoppingmall.domainrdb.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.annotation.DomainService;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.mapper.UserEntityMapper;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.dto.CreateUserRequestDto;
import shoppingmall.domainrdb.user.dto.LoginUserRequestDto;
import shoppingmall.domainrdb.user.entity.User;
import shoppingmall.domainrdb.user.entity.UserRole;
import shoppingmall.domainrdb.user.repository.UserRepository;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.UserErrorCode;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRdbService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public Long registerBuyer(final CreateUserRequestDto createUserRequest) {

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
    public Long registerSeller(final CreateUserRequestDto createUserRequest) {

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

    public Boolean isExistEmail(final String email) {
        return userRepository.existsByEmail(email);
    }


    private boolean isPasswordMatch(final LoginUserRequestDto loginUserRequestDto, final String password) {
        return bCryptPasswordEncoder.matches(loginUserRequestDto.getPassword(), password);
    }

    public User findUserByIdAndSeller(final Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER));
        if (!findUser.getUserRole().equals(UserRole.SELLER)) {
            throw new ApiException(UserErrorCode.USER_NOT_SELLER);
        }
        return findUser;
    }

    public Boolean isExistByIdAndUserRole(final Long userId, final UserRole userRole) {

        return userRepository.existsByIdAndUserRole(userId, userRole);
    }

    public UserDomain findUserByIdAndUserRole(final Long userId, final UserRole userRole) {
        User user = userRepository.findByIdAndUserRole(userId, userRole).orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER));
        return UserEntityMapper.toUserDomain(user);
    }

    public UserDomain findByUserId(final Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER));
        return UserEntityMapper.toUserDomain(user);
    }
}
