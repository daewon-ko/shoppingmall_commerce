package shoppingmall.domainrdb.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.mapper.UserEntityMapper;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.entity.User;
import shoppingmall.domainrdb.user.entity.UserRole;
import shoppingmall.domainrdb.user.repository.UserRepository;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.UserErrorCode;

@DomainRdbService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRdbService {
    private final UserRepository userRepository;


    @Transactional
    public Long registerUser(final UserDomain userDomain) {
        User user = UserEntityMapper.toUserEntity(userDomain);
        User savedUser = userRepository.save(user);
        return savedUser.getId();

    }

    public UserDomain findUserByEmail(final String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER));
        return UserEntityMapper.toUserDomain(user);
    }


    public Boolean isRegisteredEmail(final String email) {
        return userRepository.existsByEmail(email);
    }


    public Long findSellerByEmail(final String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER));
        if (!user.getUserRole().equals(UserRole.SELLER)) {
            throw new ApiException(UserErrorCode.USER_NOT_SELLER);
        }
        return user.getId();
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
