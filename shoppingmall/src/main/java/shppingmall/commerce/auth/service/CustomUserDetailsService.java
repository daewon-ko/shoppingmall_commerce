package shppingmall.commerce.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shppingmall.commerce.auth.dto.CustomUserDetails;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return CustomUserDetails
                .builder()
                .id(findUser.getId())
                .username(findUser.getEmail())
                .password(findUser.getPassword())
                .userRole(findUser.getUserRole())
                .build();

    }
}
