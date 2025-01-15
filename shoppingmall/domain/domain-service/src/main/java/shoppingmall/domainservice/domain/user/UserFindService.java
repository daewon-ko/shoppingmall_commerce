package shoppingmall.domainservice.domain.user;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.user.service.UserRdbService;

@DomainRdbService
@RequiredArgsConstructor
public class UserFindService {
    private final UserRdbService userRdbService;


}
