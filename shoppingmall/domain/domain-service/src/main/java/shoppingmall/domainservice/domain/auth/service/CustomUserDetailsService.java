package shoppingmall.domainservice.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.service.UserRdbService;
import shoppingmall.domainservice.domain.auth.dto.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRdbService userRdbService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDomain userDomain = userRdbService.findUserByEmail(email);

        return CustomUserDetails
                .builder()
                .id(userDomain.getUserId().getValue())
                .username(userDomain.getEmail())
                .password(userDomain.getEncodedPassword())
                .userRole(userDomain.getUserRole())
                .build();

    }
}
